package com.Payment.Stripe.Payment.controller;
import com.Payment.Stripe.Payment.model.Transaction;
import com.Payment.Stripe.Payment.repository.PaymentInfoRepo;
import com.Payment.Stripe.Payment.services.RefundInfoSvc;
import com.Payment.Stripe.Payment.services.TransactionSvc;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.text.ParseException;
import java.util.*;

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
        ChargeCollection charges = transactionSvc.listAllCharges();
        return charges;
    }

    //API for getting  a particular  payment  details by payment id
    @GetMapping("Search-by-Transaction-ID/{id}")
    public  Charge retriveCharge(@PathVariable String id) throws StripeException
    {
        Charge charge = transactionSvc.retriveCharge(id);
        return charge;
    }

    //API for create  refund for particular payment using payment id
    @Autowired
    private RefundInfoSvc refundInfoSvc;
        @PostMapping("Create-Refund/{id}")
        public  Refund CreateRefund(@PathVariable String id) throws StripeException
        {
            Refund refund = refundInfoSvc.CreateRefund(id);
            return refund;
        }

        //API for  getting list of all refund
        @GetMapping("Display-list-of-refund-transactions")
        public  RefundCollection listAllRefunds() throws StripeException
        {
            RefundCollection refunds = refundInfoSvc.listAllRefunds();
            return refunds;
        }

        //Api for getting list of all payment using Booking Number
        @GetMapping("/SearchBy/{bookingNumber}")
        public List<Charge> searchPaymentsByBookingNumber(@PathVariable String bookingNumber) throws StripeException
        {
            List<Charge> filteredPaymentIntents = transactionSvc.searchPaymentsByBookingNumber(bookingNumber);
            return filteredPaymentIntents;
        }

        //API for create Refund By booking number and amount
        @GetMapping("/CreateRefund/{bookingNumber}/{amount}")
        public List<Refund> refundPaymentsByBookingNumberAndAmount(@PathVariable String bookingNumber , @PathVariable Long amount) throws StripeException
        {
            List<Refund> refundList = refundInfoSvc.refundPaymentsByBookingNumberAndAmount(bookingNumber,amount);
            return refundList;
        }

        //API for Search By date Range
        @GetMapping("/SearchBy/{startDate}/{endDate}")
        public List<Charge> searchPaymentsByDateRange(@PathVariable String startDate, @PathVariable String endDate) throws StripeException, ParseException {
            List<Charge> filteredPaymentIntents = transactionSvc.searchPaymentsByDateRange(startDate,endDate);
            return filteredPaymentIntents;
        }


}