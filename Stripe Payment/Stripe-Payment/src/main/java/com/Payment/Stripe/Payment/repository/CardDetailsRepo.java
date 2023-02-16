package com.Payment.Stripe.Payment.repository;
import com.Payment.Stripe.Payment.model.CardDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDetailsRepo extends JpaRepository<CardDetails,Integer> {
}
