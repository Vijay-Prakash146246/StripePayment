package com.stripe.controller;
import com.stripe.Stripe;
import com.stripe.entities.CardDetails;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.service.PaymentSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class StripeController
{
    @Autowired
    PaymentSvc paymentSvc;
    @Value("${Stripe.apiKey}")
    String stripeKey;
    //creating payment
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
    //API for getting  a particular  payment  details

    @GetMapping("Search-by-Transaction-ID/{id}")
    public  Charge retriveCharge(@PathVariable String id) throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Charge charge = Charge.retrieve(id);
        return charge;
    }

//API for create  refund for particular payment
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
    
}
