package com.Payment.Stripe.Payment.repository;

import com.Payment.Stripe.Payment.model.Amount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmountRepo extends JpaRepository<Amount,Integer> {
}
