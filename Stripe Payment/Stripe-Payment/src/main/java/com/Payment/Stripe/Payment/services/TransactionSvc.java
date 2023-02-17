package com.Payment.Stripe.Payment.services;

import com.Payment.Stripe.Payment.model.Transaction;
import com.Payment.Stripe.Payment.repository.TransactionRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class TransactionSvc
{
    @Value("${Stripe.apiKey}")
    String stripeKey;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private  PaymentInfoSvc paymentInfoSvc;
    public Charge payUsingCard(Transaction transaction) throws StripeException
    {
        Stripe.apiKey = stripeKey;
        // Create a charge object
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", transaction.getAmount());
        chargeParams.put("currency", transaction.getCurrency());


        Map<String, Object> card = new HashMap<>();
        int cardLength = transaction.getCardNumber().length();
        card.put("number", transaction.getCardNumber());
        card.put("exp_month", transaction.getExpMonth());
        card.put("exp_year", transaction.getExpYear());
        card.put("cvc", transaction.getCvc());
        chargeParams.put("card", card);

        // Store metadata with the charge
        Map<String, String> metadata = new HashMap<>();
        metadata.put("bookingno", transaction.getBookingno());
        metadata.put("currency", transaction.getCurrency());
        metadata.put("noOfPassengers", String.valueOf(transaction.getNoOfPassengers()));
        metadata.put("supportCost", transaction.getSupportCost());
        metadata.put("supportPackage", transaction.getSupportPackage());
        metadata.put("first-name", transaction.getFirstname());
        metadata.put("middle-name", transaction.getMiddlename());
        metadata.put("last-name", transaction.getLastname());
        metadata.put("email", transaction.getEmail());
        metadata.put("Ph.Number", transaction.getPhone());
        metadata.put("Country_code", transaction.getCountrycode());
        metadata.put("Address", transaction.getAddress());
        chargeParams.put("metadata", metadata);

        transactionRepo.save(transaction);
        Charge charge = Charge.create(chargeParams);
        String responseId = charge.getId();
        paymentInfoSvc.savePaymentResponse(responseId);
        //System.out.println(charge);
        return charge;
    }
}
