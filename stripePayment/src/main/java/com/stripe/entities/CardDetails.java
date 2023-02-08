package com.stripe.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardDetails
{
    @NotNull
    String cardNumber ;
    @NotNull
    int expMonth ;
    @NotNull
    int expYear ;
    @NotNull
    String cvc ;
    @NotNull
    PersonDetails personDetails;
    @NotNull
    Amount amount;
//    @NotNull
//   public LocalDate creationdate;
//ZonedDateTime fromDate = ZonedDateTime.parse("2022-01-05T00:00:00Z");
}
