package com.Payment.Stripe.Payment.services;
import com.Payment.Stripe.Payment.model.RefundInfo;
import com.Payment.Stripe.Payment.repository.RefundInfoRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.ChargeCollection;
import com.stripe.model.Refund;
import com.stripe.model.RefundCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RefundInfoSvc
{
    @Value("${Stripe.apiKey}")
    String stripeKey;
    @Autowired
    private RefundInfoRepo refundInfoRepo;
    public void saveRefundResponse(Refund refund) throws StripeException
    {
        RefundInfo refundInfo = new RefundInfo();
        refundInfo.setRefundAmount(refund.getAmount());
        refundInfo.setRefundBalanceTransactionId(refund.getBalanceTransaction());
        refundInfo.setRefundChargeId(refund.getCharge());
        refundInfo.setCurrency(refund.getCurrency());
        refundInfo.setRefundId(refund.getId());
        refundInfo.setRefundStatus(refund.getStatus());
        refundInfoRepo.save(refundInfo);
    }

    //method for create  refund for particular payment using payment id
    public  Refund CreateRefund(@PathVariable String id) throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params = new HashMap<>();
        params.put("charge", id);
        Refund refund = Refund.create(params);
        saveRefundResponse(refund);
        return refund;

    }

    //method for  getting list of all refund
    public RefundCollection listAllRefunds() throws StripeException
    {
        Stripe.apiKey = stripeKey;
        Map<String, Object> params = new HashMap<>();
        //params.put("limit", 3)
        RefundCollection refunds = Refund.list(params);
        return refunds;
    }

    //method for create Refund By booking number and amount
    public List<Refund> refundPaymentsByBookingNumberAndAmount(String bookingNumber , Long amount) throws StripeException
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
            saveRefundResponse(refund);
        }

        return refundList;
    }
}
