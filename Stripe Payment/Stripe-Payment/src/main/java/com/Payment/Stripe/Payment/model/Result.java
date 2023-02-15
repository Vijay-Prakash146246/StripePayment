package com.Payment.Stripe.Payment.model;

import com.Payment.Stripe.Payment.repository.ResultRepo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Payment_Response")
public class Result
{
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
   // @Column(name = "Id")
   // private int id;
    private  String transactionId;
    private Long amount ;
    private String balanceTransaction;
   private  String currency ;
    private Long getCreated ;
    private String status;
    private String address;
    private  String countryCode;
    private String phoneNumber;
    private String bookingNumber;
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String noOfPassenger;
    private String supportCost;
    private String supportPackage;
//    private  boolean paidStatus;
    private String paymentMode;
    //private boolean refundStatus;
}
