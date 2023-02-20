package com.Payment.Stripe.Payment.controller;
import com.Payment.Stripe.Payment.model.Transaction;
import com.Payment.Stripe.Payment.repository.PaymentInfoRepo;
import com.Payment.Stripe.Payment.services.RefundInfoSvc;
import com.Payment.Stripe.Payment.services.TransactionSvc;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class StripeController
{
    @Autowired
    private PaymentInfoRepo paymentInfoRepo;
    @Value("${Stripe.apiKey}")
    String stripeKey;
    @Autowired
    private TransactionSvc transactionSvc;


    //API for creating payment
    @PostMapping("/Create-Payment")
    public Charge payByCard(@RequestBody @Valid Transaction transaction) throws StripeException
    {
        Charge charge = transactionSvc.payUsingCard(transaction);
        return charge;
    }

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
    //API for getting  a particular  payment  details by payment id
    @GetMapping("Search-by-Transaction-ID/{id}")
    public  Charge retriveCharge(@PathVariable String id) throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Charge charge = Charge.retrieve(id);
        return charge;
    }

//API for create  refund for particular payment using payment id
@Autowired
private RefundInfoSvc refundInfoSvc;
    @PostMapping("Create-Refund/{id}")
    public  Refund CreateRefund(@PathVariable String id) throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params = new HashMap<>();
        params.put("charge", id);
        Refund refund = Refund.create(params);
        refundInfoSvc.saveRefundResponse(refund);
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

    //Api for getting list of all payment using Booking Number
    @GetMapping("/SearchBy/{bookingNumber}")
    public List<Charge> searchPaymentsByBookingNumber(@PathVariable String bookingNumber) throws StripeException
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

    @GetMapping("/CreateRefund/{bookingNumber}/{amount}")
    public List<Refund> refundPaymentsByBookingNumber(@PathVariable String bookingNumber , @PathVariable Long amount) throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params1 = new HashMap<>();
        // params.put("limit", 3);
        ChargeCollection charges = Charge.list(params1);
        List<Charge> filteredPaymentIntents = StreamSupport.stream(charges.getData().spliterator(), false)
                .filter(payment -> bookingNumber.equals(payment.getMetadata().get("bookingno")) && amount.equals(payment.getAmount()) )
                .collect(Collectors.toList());
        List<Refund> refundList = new ArrayList<>();
        for (Charge filteredPaymentIntent : filteredPaymentIntents) {
            Map<String, Object> params = new HashMap<>();
            String id = filteredPaymentIntent.getId();
            params.put("charge", id);
            Refund refund = Refund.create(params);
            refundList.add(refund);
            refundInfoSvc.saveRefundResponse(refund);
        }

        return refundList;
    }









//Code under test






}

