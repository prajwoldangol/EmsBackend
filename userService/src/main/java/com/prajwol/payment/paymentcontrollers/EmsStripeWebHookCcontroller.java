package com.prajwol.payment.paymentcontrollers;

import com.prajwol.payment.EmsPaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/employer/webhooks")
@Log4j2
public class EmsStripeWebHookCcontroller {
//    @Value("${stripe.webhook.secret}")
//    private String endpointSecret;
    private EmsPaymentService emsPaymentService;
    @Autowired
    public EmsStripeWebHookCcontroller(EmsPaymentService emsPaymentService) {
        this.emsPaymentService = emsPaymentService;
    }

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        Event event = null;
        try {
            event = Webhook.constructEvent(payload, sigHeader, "whsec_f008282b97104f33cd86fdfb72c4294b7a77a8b6a55360cbd5078d3f825d8f9d");
        } catch (SignatureVerificationException e) {
            System.out.println("Failed signature verification");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;

        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        }

        switch (event.getType()) {
            case "payment_intent.succeeded":
              // log.info(stripeObject);

                break;
            case "checkout.session.completed":
                // ...
//                log.info("AFTER checkout is completed"+ stripeObject);
                try {
                    emsPaymentService.updateCustomerAndPayment(stripeObject);
                } catch (StripeException e) {
                    throw new RuntimeException(e);
                }
                break;
            // ... handle other event types
            default:
                // Unexpected event type
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);

    }

}
