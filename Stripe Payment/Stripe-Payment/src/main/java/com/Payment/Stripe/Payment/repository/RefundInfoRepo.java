package com.Payment.Stripe.Payment.repository;
import com.Payment.Stripe.Payment.model.RefundInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundInfoRepo extends JpaRepository<RefundInfo,String> {
}
