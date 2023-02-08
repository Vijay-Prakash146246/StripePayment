package com.stripe.service;

import com.stripe.Stripe;
import com.stripe.entities.CardDetails;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class PaymentSvc
{
    @Value("${Stripe.apiKey}")
    String stripeKey;
    public Charge payUsingCard(CardDetails cardDetails) throws StripeException
    {
        Stripe.apiKey = stripeKey;

        // Create a charge object
        Map<String, Object> chargeParams = new HashMap<>();

        chargeParams.put("amount", cardDetails.getAmount().getAmount()); // in cents
        chargeParams.put("currency", cardDetails.getAmount().getCurrency());

        Map<String, Object> card = new HashMap<>();
        //card.put("number", cardNumber);
        card.put("number", cardDetails.getCardNumber());
        card.put("exp_month", cardDetails.getExpMonth());
        card.put("exp_year", cardDetails.getExpYear());
        card.put("cvc", cardDetails.getCvc());
        //card.put("creationdate", cardDetails.getFromDate());
        chargeParams.put("card", card);

        // Store metadata with the charge
        Map<String, String> metadata = new HashMap<>();
        metadata.put("first-name", cardDetails.getPersonDetails().getFirstname());
        metadata.put("middle-name", cardDetails.getPersonDetails().getMiddlename());
        metadata.put("last-name", cardDetails.getPersonDetails().getLastname());
        metadata.put("email", cardDetails.getPersonDetails().getEmail());
        metadata.put("Ph.Number", cardDetails.getPersonDetails().getPhone());
        metadata.put("Country_code", cardDetails.getPersonDetails().getCountrycode());
        metadata.put("Address", cardDetails.getPersonDetails().getAddress());
        chargeParams.put("metadata", metadata);

        Charge charge = Charge.create(chargeParams);
        //System.out.println(charge);
        return charge;
    }
}
