package com.Payment.Stripe.Payment.repository;

import com.Payment.Stripe.Payment.model.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentInfoRepo extends JpaRepository<PaymentInfo,String>
{
}
