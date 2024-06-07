package com.prajwol.payment.paymentcontrollers;

import com.prajwol.payment.EmsPaymentService;
import com.prajwol.payment.EmsPaymentDto;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/employer/pay")
public class EmsSubscriptionController {

    private EmsPaymentService emsPayment;
    @Autowired
    public EmsSubscriptionController(EmsPaymentService emsPayment) {
        this.emsPayment = emsPayment;
    }
    @PostMapping
    public String createSubscription(@RequestBody EmsPaymentDto paymentDto) {
        try {
            return emsPayment.createPayment(paymentDto);
        } catch (StripeException e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/checkout-session")
    public Session getCheckoutSession(@RequestParam String sessionId) throws StripeException {
        return Session.retrieve(sessionId);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<String> getCustomer(@PathVariable String customerId) {
        try {
            Customer customer = emsPayment.getCustomer(customerId);
            log.info(customer);
            if (customer != null) {
                return ResponseEntity.ok(customer.getEmail());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (StripeException e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/customer/{customerId}/subscriptions")
    public ResponseEntity<List<Subscription>> getCustomerSubscriptions(@PathVariable String customerId) {
        try {
            List<Subscription> subscriptions = emsPayment.getCustomerSubscriptions(customerId);
            return ResponseEntity.ok(subscriptions);
        } catch (StripeException e) {
            return ResponseEntity.status(400).body(null);
        }
    }
}
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