package com.Payment.Stripe.Payment.repository;

import com.Payment.Stripe.Payment.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepo extends JpaRepository<UserInfo,Long>
{

}
