package com.Payment.Stripe.Payment.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PersonDetails")
public class PersonDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "Person_Id")
    int personId;
    @NotNull
    @Column(name = "Booking_No")
    String bookingno;
    @NotNull
    @Column(name = "Currency")
    String currency;
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
