package com.Payment.Stripe.Payment.services;

import com.Payment.Stripe.Payment.model.PaymentInfo;
import com.Payment.Stripe.Payment.repository.PaymentInfoRepo;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentInfoSvc
{
    @Autowired
    private PaymentInfoRepo paymentInfoRepo;
//    Optional<Result> findById(String transactionId)
//    {
//        return resultRepo.findById(transactionId);
//    }
    public void savePaymentResponse(String id) throws StripeException {
        //Stripe.apiKey = stripeKey;
        Charge charge = Charge.retrieve(id);

        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setAmount(charge.getAmount());
        paymentInfo.setBalanceTransaction(charge.getBalanceTransaction());
        paymentInfo.setCurrency(charge.getCurrency());
        paymentInfo.setGetCreated(charge.getCreated());
        paymentInfo.setStatus(charge.getStatus());
        paymentInfo.setTransactionId(charge.getId());
        //result.setAddress(charge.getMetadata().get(1));
        paymentInfo.setAddress(charge.getMetadata().get("Address"));
        paymentInfo.setCountryCode(charge.getMetadata().get("Country_code"));
        paymentInfo.setPhoneNumber(charge.getMetadata().get("Ph.Number"));
        paymentInfo.setBookingNumber(charge.getMetadata().get("bookingno"));
        paymentInfo.setEmail(charge.getMetadata().get("email"));
        paymentInfo.setFirstName(charge.getMetadata().get("first-name"));
        paymentInfo.setMiddleName(charge.getMetadata().get("middle-name"));
        paymentInfo.setLastName(charge.getMetadata().get("last-name"));
        paymentInfo.setNoOfPassenger(charge.getMetadata().get("noOfPassengers"));
        paymentInfo.setSupportCost(charge.getMetadata().get("supportCost"));
        paymentInfo.setSupportPackage(charge.getMetadata().get("supportPackage"));
        paymentInfo.setPaymentMode(charge.getPaymentMethodDetails().getType());
        paymentInfoRepo.save(paymentInfo);

    }
}
