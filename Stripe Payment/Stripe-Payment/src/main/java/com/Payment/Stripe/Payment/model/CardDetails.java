package com.Payment.Stripe.Payment.model;

import com.stripe.util.StringUtils;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.print.DocFlavor;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CardDetails")
public class CardDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int id;
    @NotNull
    @Column(name = "Card_Number")
    String cardNumber ;
    @NotNull
    @Column(name = "Exp_Month")
    int expMonth ;
    @NotNull
    @Column(name = "Exp_Year")
    int expYear ;
    @NotNull
    @Column(name = "cvc")
    String cvc ;
    @NotNull
    //@Transient
   @OneToOne(cascade = CascadeType.ALL)
   @JoinColumn(name = "Person_Id")
    PersonDetails personDetails;
    @NotNull
    //@Transient
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Amount_Id")
    Amount amount;

}
