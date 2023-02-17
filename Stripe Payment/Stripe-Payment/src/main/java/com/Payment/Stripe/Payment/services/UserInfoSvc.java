package com.Payment.Stripe.Payment.services;

import com.Payment.Stripe.Payment.model.CardDetails;
import com.Payment.Stripe.Payment.model.UserInfo;
import com.Payment.Stripe.Payment.repository.UserInfoRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class UserInfoSvc
{
    @Value("${Stripe.apiKey}")
    String stripeKey;
    @Autowired
    private UserInfoRepo userInfoRepo;
    @Autowired
    private  PaymentInfoSvc paymentInfoSvc;
    public Charge payUsingCard(UserInfo userInfo) throws StripeException
    {
        Stripe.apiKey = stripeKey;
        // Create a charge object
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount",userInfo.getAmount());
        chargeParams.put("currency", userInfo.getCurrency());


        Map<String, Object> card = new HashMap<>();
        int cardLength = userInfo.getCardNumber().length();
        card.put("number", userInfo.getCardNumber());
        card.put("exp_month", userInfo.getExpMonth());
        card.put("exp_year", userInfo.getExpYear());
        card.put("cvc", userInfo.getCvc());
        chargeParams.put("card", card);

        // Store metadata with the charge
        Map<String, String> metadata = new HashMap<>();
        metadata.put("bookingno", userInfo.getBookingno());
        metadata.put("currency", userInfo.getCurrency());
        metadata.put("noOfPassengers", String.valueOf(userInfo.getNoOfPassengers()));
        metadata.put("supportCost", userInfo.getSupportCost());
        metadata.put("supportPackage", userInfo.getSupportPackage());
        metadata.put("first-name", userInfo.getFirstname());
        metadata.put("middle-name", userInfo.getMiddlename());
        metadata.put("last-name", userInfo.getLastname());
        metadata.put("email", userInfo.getEmail());
        metadata.put("Ph.Number", userInfo.getPhone());
        metadata.put("Country_code", userInfo.getCountrycode());
        metadata.put("Address", userInfo.getAddress());
        chargeParams.put("metadata", metadata);

        userInfoRepo.save(userInfo);
        Charge charge = Charge.create(chargeParams);
        String responseId = charge.getId();
        paymentInfoSvc.savePaymentResponse(responseId);
        //System.out.println(charge);
        return charge;
    }
}
