package com.Payment.Stripe.Payment.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Amount")
public class Amount
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "Amount_Id")
    int amountId;
    @NotNull
    @Column(name = "Amount")
    int amount;
    @NotNull
    @Column(name = "Currency")
    String currency;
    @CreationTimestamp
    @Column(name = "Paid_At",nullable = false,updatable = false)
    private Date paidAt;
}
