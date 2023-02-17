package com.Payment.Stripe.Payment.model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Payment_Info")
public class PaymentInfo
{
    @Id
    @Column(name = "transactionId")
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
    private String paymentMode;
    @CreationTimestamp
    @Column(updatable = false)
    private Date paidAt;
}
