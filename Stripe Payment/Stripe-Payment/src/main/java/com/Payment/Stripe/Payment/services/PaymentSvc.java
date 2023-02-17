package com.Payment.Stripe.Payment.services;
import com.Payment.Stripe.Payment.model.CardDetails;
import com.Payment.Stripe.Payment.repository.CardDetailsRepo;
import com.Payment.Stripe.Payment.repository.PersonDetailsRepo;
import com.Payment.Stripe.Payment.repository.UserInfoRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentSvc
{
    @Value("${Stripe.apiKey}")
    String stripeKey;
    @Autowired
    private CardDetailsRepo cardDetailsRepo;
    @Autowired
    private PersonDetailsRepo personDetailsRepo;
    @Autowired
    private PaymentInfoSvc paymentInfoSvc;
    public Charge payUsingCard(CardDetails cardDetails) throws StripeException
    {
        Stripe.apiKey = stripeKey;
        // Create a charge object
        Map<String, Object> chargeParams = new HashMap<>();

        chargeParams.put("amount", cardDetails.getAmount().getAmount()); // in cents
        chargeParams.put("currency", cardDetails.getAmount().getCurrency());


        Map<String, Object> card = new HashMap<>();
        int cardLength = cardDetails.getCardNumber().length();
        card.put("number", cardDetails.getCardNumber());
        card.put("exp_month", cardDetails.getExpMonth());
        card.put("exp_year", cardDetails.getExpYear());
        card.put("cvc", cardDetails.getCvc());
        chargeParams.put("card", card);

        // Store metadata with the charge
        Map<String, String> metadata = new HashMap<>();
        metadata.put("bookingno", cardDetails.getPersonDetails().getBookingno());
        metadata.put("currency", cardDetails.getPersonDetails().getCurrency());
        metadata.put("noOfPassengers", String.valueOf(cardDetails.getPersonDetails().getNoOfPassengers()));
        metadata.put("supportCost", cardDetails.getPersonDetails().getSupportCost());
        metadata.put("supportPackage", cardDetails.getPersonDetails().getSupportPackage());
        metadata.put("first-name", cardDetails.getPersonDetails().getFirstname());
        metadata.put("middle-name", cardDetails.getPersonDetails().getMiddlename());
        metadata.put("last-name", cardDetails.getPersonDetails().getLastname());
        metadata.put("email", cardDetails.getPersonDetails().getEmail());
        metadata.put("Ph.Number", cardDetails.getPersonDetails().getPhone());
        metadata.put("Country_code", cardDetails.getPersonDetails().getCountrycode());
        metadata.put("Address", cardDetails.getPersonDetails().getAddress());
        chargeParams.put("metadata", metadata);

        cardDetailsRepo.save(cardDetails);
        Charge charge = Charge.create(chargeParams);
        String responseId = charge.getId();
        paymentInfoSvc.savePaymentResponse(responseId);
        //System.out.println(charge);
        return charge;
    }
}
