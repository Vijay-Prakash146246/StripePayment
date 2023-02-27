package com.Payment.Stripe.Payment.services;
import com.Payment.Stripe.Payment.model.Transaction;
import com.Payment.Stripe.Payment.repository.TransactionRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TransactionSvc
{
    @Value("${Stripe.apiKey}")
    String stripeKey;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private  PaymentInfoSvc paymentInfoSvc;

    //Method for creating payment
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

    //method for getting list of all payment
    public ChargeCollection listAllCharges() throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params = new HashMap<>();
        // params.put("limit", 3);
        ChargeCollection charges = Charge.list(params);
        return charges;
    }

    //method for getting  a particular  payment  details by payment id
    public  Charge retriveCharge(String id) throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Charge charge = Charge.retrieve(id);
        return charge;
    }

    //method for getting list of all payment using Booking Number
    public List<Charge> searchPaymentsByBookingNumber(String bookingNumber) throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params1 = new HashMap<>();
        // params.put("limit", 3);
        ChargeCollection charges = Charge.list(params1);
        List<Charge> filteredPaymentIntents = StreamSupport.stream(charges.getData().spliterator(), false)
                .filter(payment -> bookingNumber.equals(payment.getMetadata().get("bookingno")))
                .collect(Collectors.toList());
        return filteredPaymentIntents;
    }

    //method for Search By date Range
    public List<Charge> searchPaymentsByDateRange(String startDate, String endDate) throws StripeException, ParseException {
        Stripe.apiKey = stripeKey;
        // Convert start and end date strings to Date objects
        Date startDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date endDate1 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
        Map<String, Object> params1 = new HashMap<>();
        // params.put("limit", 3);
        ChargeCollection charges = Charge.list(params1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        List<Charge> filteredPaymentIntents = StreamSupport.stream(charges.getData().spliterator(), false)
                .filter(payment -> (simpleDateFormat.format(payment.getCreated()* 1000L).compareTo(startDate)>=0
                        &&simpleDateFormat.format(payment.getCreated()* 1000L).compareTo(endDate)<=0 ))
                .collect(Collectors.toList());
        return filteredPaymentIntents;
    }
}
