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

//API for getting list of all payment

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

    //API for creating payment using card

//    @PostMapping("/payByCard")
//    public Charge payByCard() throws StripeException
//    {
//        Stripe.apiKey = stripeKey;
//        // Get the credit card information from the client
//        String cardNumber = "4242 4242 4242 4242";
//        int expMonth = 12;
//        int expYear = 2025;
//        String cvc = "123";
//
//        // Create a charge object
//        Map<String, Object> chargeParams = new HashMap<>();
//
//        chargeParams.put("amount", 5500); // in cents
//        chargeParams.put("currency", "usd");
//
//        Map<String, Object> card = new HashMap<>();
//        card.put("number", cardNumber);
//        card.put("exp_month", expMonth);
//        card.put("exp_year", expYear);
//        card.put("cvc", cvc);
//        chargeParams.put("card", card);
//
//        // Store metadata with the charge
//        Map<String, String> metadata = new HashMap<>();
//        metadata.put("Name", "Vijay Kumar");
//        metadata.put("email", "vp146@gamil.com");
//        metadata.put("Ph.Number", "9829326858");
//        metadata.put("Country_code", "+91");
//        metadata.put("Address", "Near Salt lake  kolkata");
//        chargeParams.put("metadata", metadata);
//
//        Charge charge = Charge.create(chargeParams);
//         //System.out.println(charge);
//         return charge;
//    }

    //Code for Refund start from here
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

//creating payment using postman data
    @PostMapping("/Create-Payment")
    public Charge payUsingCard(@RequestBody @Valid CardDetails cardDetails) throws StripeException
    {
         Charge charge = paymentSvc.payUsingCard(cardDetails);
        return charge;
    }












    //list all charges based on particular date
    @GetMapping("listAllChargesBasedOnDate")
    public  ChargeCollection listAllChargesBasedOnDate() throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params = new HashMap<>();
        // params.put("limit", 3);
        ChargeCollection charges = Charge.list(params);
        return charges;
    }


    @GetMapping("listOfAllPayment")
    public  ChargeCollection listOfAllPayment() throws StripeException
    {
        Stripe.apiKey = stripeKey;
        ZonedDateTime fromDate = ZonedDateTime.parse("2022-01-01T00:00:00Z");
        ZonedDateTime toDate = ZonedDateTime.parse("2022-01-31T23:59:59Z");

        Map<String, Object> params = new HashMap<>();
        params.put("created", new HashMap<String, Object>() {{
            put("gte", fromDate.toInstant().getEpochSecond());
            put("lte", toDate.toInstant().getEpochSecond());
        }});

        ChargeCollection charges = Charge.list(params);
        for (Charge charge : charges.getData()) {
            System.out.println(charge.getId());
        }

        //ChargeCollection charges = Charge.list(params);
        return charges;
    }
}
