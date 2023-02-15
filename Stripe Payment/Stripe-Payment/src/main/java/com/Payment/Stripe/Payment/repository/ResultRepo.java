package com.Payment.Stripe.Payment.repository;

import com.Payment.Stripe.Payment.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepo extends JpaRepository<Result,String>
{
}
