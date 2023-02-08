package com.stripe.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDetails
{
    String firstname;
    String middlename;
    String lastname;
    String email;
    String phone;
    String countrycode;
    String address;
}
