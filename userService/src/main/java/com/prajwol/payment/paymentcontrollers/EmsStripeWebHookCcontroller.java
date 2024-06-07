package com.prajwol.payment.paymentcontrollers;

import com.prajwol.payment.EmsPaymentService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
            // instructions on how to handle this case, or return an error here.
        }

        switch (event.getType()) {
            case "payment_intent.succeeded":
               log.info(stripeObject);
               emsPaymentService.updateCustomerAndPayment(stripeObject);
                break;
            case "payment_method.attached":
                // ...
                break;
            // ... handle other event types
            default:
                // Unexpected event type
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new ResponseEntity<>("Success", HttpStatus.OK);
//        Event event = null;
//
//        try {
//            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
//        } catch (SignatureVerificationException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid signature");
//        }
//
//        switch (event.getType()) {
//            case "invoice.payment_succeeded":
//                handleInvoicePaymentSucceeded(event);
//                break;
//            case "invoice.payment_failed":
//                handleInvoicePaymentFailed(event);
//                break;
//            case "customer.subscription.deleted":
//                handleSubscriptionDeleted(event);
//                break;
//            default:
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unhandled event type");
//        }
//
//        return ResponseEntity.ok("Success");
    }

    private void handleInvoicePaymentSucceeded(Event event) {
        Invoice invoice = (Invoice) event.getDataObjectDeserializer().getObject().orElse(null);
        if (invoice != null) {
            String customerId = invoice.getCustomer();
            String subscriptionId = invoice.getSubscription();
            // Update your application's state to reflect the successful payment


        }
    }

    private void handleInvoicePaymentFailed(Event event) {
        Invoice invoice = (Invoice) event.getDataObjectDeserializer().getObject().orElse(null);
        if (invoice != null) {
            String customerId = invoice.getCustomer();
            String subscriptionId = invoice.getSubscription();
            // Update your application's state to reflect the failed payment
        }
    }

    private void handleSubscriptionDeleted(Event event) {
        Subscription subscription = (Subscription) event.getDataObjectDeserializer().getObject().orElse(null);
        if (subscription != null) {
            String customerId = subscription.getCustomer();
            String subscriptionId = subscription.getId();
            // Update your application's state to reflect the subscription cancellation
        }
    }
}
