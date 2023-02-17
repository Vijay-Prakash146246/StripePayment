package com.Payment.Stripe.Payment.controller;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

    @RestController
    class StripePaymentController {
        @Value("${Stripe.apiKey}")
        String stripeKey;

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
    }


