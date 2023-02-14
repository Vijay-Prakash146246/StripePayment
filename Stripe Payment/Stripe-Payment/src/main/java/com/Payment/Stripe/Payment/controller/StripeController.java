package com.Payment.Stripe.Payment.controller;

import com.Payment.Stripe.Payment.model.CardDetails;
import com.Payment.Stripe.Payment.services.PaymentSvc;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import com.stripe.model.Refund;
import com.stripe.model.RefundCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StripeController
{
    @Autowired
    PaymentSvc paymentSvc;
    @Value("${Stripe.apiKey}")
    String stripeKey;
    //API for creating payment
    @PostMapping("/Create-Payment")
    public Charge payUsingCard(@RequestBody @Valid CardDetails cardDetails) throws StripeException
    {
        Charge charge = paymentSvc.payUsingCard(cardDetails);
        return charge;
    }

    ///API for getting list of all payment
    @GetMapping("Display-list-of-Payment-Transactions")
    public  ChargeCollection listAllCharges() throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params = new HashMap<>();
       // params.put("limit", 3);
        ChargeCollection charges = Charge.list(params);
        return charges;
    }
    //API for getting  a particular  payment  details by payment id
    @GetMapping("Search-by-Transaction-ID/{id}")
    public  Charge retriveCharge(@PathVariable String id) throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Charge charge = Charge.retrieve(id);
        return charge;
    }

//API for create  refund for particular payment using payment id
    @PostMapping("Create-Refund/{id}")
    public  Refund CreateRefund(@PathVariable String id) throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params = new HashMap<>();
        params.put("charge", id);
        Refund refund = Refund.create(params);
        return refund;

    }
    //API for  getting list of all refund
    @GetMapping("Display-list-of-refund-transactions")
    public  RefundCollection listAllRefunds() throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params = new HashMap<>();
        //params.put("limit", 3)
        RefundCollection refunds = Refund.list(params);
        return refunds;
    }














//Code under test
    @GetMapping("Display-list-of-Payment-Transactions1")
    public  ChargeCollection listAllCharges1() throws StripeException {
        Stripe.apiKey = stripeKey;
//        Map<String, Object> params = new HashMap<>();
//        // params.put("limit", 3);
//        ChargeCollection charges = Charge.list(params);
//        return charges;
        long startTimestamp = 1546300800; // start of Jan 1st, 2019 in UNIX timestamp
        long endTimestamp = 1609439200; // end of Dec 31st, 2020 in UNIX timestamp

        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("created", new HashMap<String, Object>() {{
            put("gte", startTimestamp);
            put("lte", endTimestamp);
        }});

        ChargeCollection charges = Charge.list(chargeParams);

        for (Charge charge : charges.getData()) {
            System.out.println(charge.getId() + " " + charge.getCreated());

        }
        return charges;
    }
}
