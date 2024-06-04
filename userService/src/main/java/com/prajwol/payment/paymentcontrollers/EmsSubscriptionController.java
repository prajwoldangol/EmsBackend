//package com.prajwol.payment.paymentcontrollers;
//@RestController
//@RequestMapping("/api/subscription")
//public class EmsSubscriptionController {

//
//    @PostMapping("/create")
//    public String createSubscription(@RequestParam String email, @RequestParam String priceId) throws StripeException {
//        SessionCreateParams params =
//                SessionCreateParams.builder()
//                        .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
//                        .setCustomerEmail(email)
//                        .addLineItem(
//                                SessionCreateParams.LineItem.builder()
//                                        .setQuantity(1L)
//                                        .setPrice(priceId)
//                                        .build())
//                        .setSuccessUrl("http://localhost:3000/success?session_id={CHECKOUT_SESSION_ID}")
//                        .setCancelUrl("http://localhost:3000/cancel")
//                        .build();
//
//        Session session = Session.create(params);
//
//        return session.getUrl();
//    }
//
//    @PostMapping("/cancel")
//    public String cancelSubscription(@RequestParam String subscriptionId) throws StripeException {
//        Subscription subscription = Subscription.retrieve(subscriptionId);
//        SubscriptionCancelParams params = SubscriptionCancelParams.builder()
//                .setInvoiceNow(true)
//                .setProrate(true)
//                .build();
//        subscription.cancel(params);
//
//        return "Subscription canceled successfully";
//    }
//}
//PriceCreateParams params =
//        PriceCreateParams.builder()
//                .setCurrency("usd")
//                .setUnitAmount(999) // $9.99 USD
//                .setRecurring(PriceCreateParams.Recurring.builder()
//                        .setInterval("month")
//                        .build())
//                .setProduct("prod_1234567890") // Replace with your product ID
//                .build();
//
//Price price = Price.create(params);
//String priceId = price.getId();