package com.Payment.Stripe.Payment.services;

import com.Payment.Stripe.Payment.model.Result;
import com.Payment.Stripe.Payment.repository.ResultRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Service
public class ResultSvc
{
    @Autowired
    private ResultRepo resultRepo;
//    Optional<Result> findById(String transactionId)
//    {
//        return resultRepo.findById(transactionId);
//    }
    public void savePaymentResponse(String id) throws StripeException {
        //Stripe.apiKey = stripeKey;
        Charge charge = Charge.retrieve(id);

        Result result = new Result();
        result.setAmount(charge.getAmount());
        result.setBalanceTransaction(charge.getBalanceTransaction());
        result.setCurrency(charge.getCurrency());
        result.setGetCreated(charge.getCreated());
        result.setStatus(charge.getStatus());
        result.setTransactionId(charge.getId());
        //result.setAddress(charge.getMetadata().get(1));
        result.setAddress(charge.getMetadata().get("Address"));
        result.setCountryCode(charge.getMetadata().get("Country_code"));
        result.setPhoneNumber(charge.getMetadata().get("Ph.Number"));
        result.setBookingNumber(charge.getMetadata().get("bookingno"));
        result.setEmail(charge.getMetadata().get("email"));
        result.setFirstName(charge.getMetadata().get("first-name"));
        result.setMiddleName(charge.getMetadata().get("middle-name"));
        result.setLastName(charge.getMetadata().get("last-name"));
        result.setNoOfPassenger(charge.getMetadata().get("noOfPassengers"));
        result.setSupportCost(charge.getMetadata().get("supportCost"));
        result.setSupportPackage(charge.getMetadata().get("supportPackage"));
        result.setPaymentMode(charge.getPaymentMethodDetails().getType());
        resultRepo.save(result);

    }
}
