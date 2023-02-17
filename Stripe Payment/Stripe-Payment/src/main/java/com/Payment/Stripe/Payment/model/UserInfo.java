package com.Payment.Stripe.Payment.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@Table(name = "User_Info")
public class UserInfo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull
    @Column(name = "Amount")
    int amount;
    @NotNull
    @Column(name = "Currency")
    String currency;
    @CreationTimestamp
    @Column(name = "Paid_At",nullable = false,updatable = false)
    private Date paidAt;
    @NotNull
    @Transient
    @Column(name = "Card_Number")
    String cardNumber ;
    @NotNull
    @Column(name = "Exp_Month")
    int expMonth ;
    @NotNull
    @Column(name = "Exp_Year")
    int expYear ;
    @NotNull
    @Transient
    @Column(name = "cvc")
    String cvc ;
    @NotNull
    @Column(name = "Booking_No")
    String bookingno;
    @NotNull
    @Column(name = "No_Of_Passengers")
    int noOfPassengers;
    @Column(name = "Support_Cost")
    String supportCost;
    @Column(name = "Support_Package")
    String supportPackage;
    @Column(name = "First_Name")
    String firstname;
    @Column(name = "Middle_name")
    String middlename;
    @Column(name = "Last_Name")
    String lastname;
    @Column(name = "Email")
    String email;
    @Column(name = "Phone_No")
    String phone;
    @Column(name = "Country_Code")
    String countrycode;
    @Column(name = "Address")
    String address;
}
