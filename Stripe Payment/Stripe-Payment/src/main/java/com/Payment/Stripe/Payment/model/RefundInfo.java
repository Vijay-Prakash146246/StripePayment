package com.Payment.Stripe.Payment.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Refund_Info")
public class RefundInfo
{
        @Id
        private String refundChargeId;
        private  String refundId;
        private long refundAmount;
        private String refundBalanceTransactionId;
        private String currency;
        private  String refundStatus;
        @CreationTimestamp
        @Column(updatable = false)
        private Date refundAt;
        @OneToOne(cascade = CascadeType.ALL)
        private PaymentInfo paymentInfo;

}
