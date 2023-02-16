package com.Payment.Stripe.Payment.services;
import com.Payment.Stripe.Payment.model.RefundInfo;
import com.Payment.Stripe.Payment.repository.RefundInfoRepo;
import com.stripe.exception.StripeException;
import com.stripe.model.Refund;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefundInfoSvc
{
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
}
