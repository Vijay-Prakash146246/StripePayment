package com.Payment.Stripe.Payment.repository;

import com.Payment.Stripe.Payment.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction,Long>
{

}
