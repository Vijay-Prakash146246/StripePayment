package com.Payment.Stripe.Payment.repository;
import com.Payment.Stripe.Payment.model.PersonDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonDetailsRepo extends JpaRepository<PersonDetails,Integer>
{
}
