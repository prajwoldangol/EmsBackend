//package com.prajwol.payment.paymentcontrollers;
//@RestController
//@RequestMapping("/api/webhooks")
//public class EmsStripeWebHookCcontroller {
//    @Value("${stripe.webhook.secret}")
//    private String endpointSecret;
//
//    @PostMapping("/stripe")
//    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
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
//    }
//
//    private void handleInvoicePaymentSucceeded(Event event) {
//        Invoice invoice = (Invoice) event.getDataObjectDeserializer().getObject().orElse(null);
//        if (invoice != null) {
//            String customerId = invoice.getCustomer();
//            String subscriptionId = invoice.getSubscription();
//            // Update your application's state to reflect the successful payment
//        }
//    }
//
//    private void handleInvoicePaymentFailed(Event event) {
//        Invoice invoice = (Invoice) event.getDataObjectDeserializer().getObject().orElse(null);
//        if (invoice != null) {
//            String customerId = invoice.getCustomer();
//            String subscriptionId = invoice.getSubscription();
//            // Update your application's state to reflect the failed payment
//        }
//    }
//
//    private void handleSubscriptionDeleted(Event event) {
//        Subscription subscription = (Subscription) event.getDataObjectDeserializer().getObject().orElse(null);
//        if (subscription != null) {
//            String customerId = subscription.getCustomer();
//            String subscriptionId = subscription.getId();
//            // Update your application's state to reflect the subscription cancellation
//        }
//    }
//}
