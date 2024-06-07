package com.prajwol.payment;

import at.favre.lib.idmask.IdMask;
import com.prajwol.dto.EmsEmployerResponseDto;
import com.prajwol.dto.EmsSubscriptionDto;
import com.prajwol.entity.EmsEmployer;
import com.prajwol.entity.EmsRole;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.service.EmsEmployerService;
import com.prajwol.userservice.IdObfuscationService;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.StripeObject;
import com.stripe.model.Subscription;
import com.stripe.model.SubscriptionCollection;
import com.stripe.model.checkout.Session;
import com.stripe.param.SubscriptionListParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class EmsPaymentService {
    private EmsEmployerService emsEmployerService;
    private IdObfuscationService idObfuscationService;
    private IdMask<Long> idMask;
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        com.stripe.Stripe.apiKey = stripeApiKey;
    }
    @Autowired
    public EmsPaymentService(EmsEmployerService emsEmployerService, IdObfuscationService idObfuscationService) {
        this.emsEmployerService = emsEmployerService;
        this.idObfuscationService = idObfuscationService;
        this.idMask = idObfuscationService.idMask();
    }

    public String createPayment(EmsPaymentDto paymentDto) throws StripeException {
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                        .setCustomerEmail(paymentDto.getUsername())
//                        .setCustomer(paymentDto.getUserId())
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPrice(paymentDto.getPriceId())
                                        .build())
                        .setSuccessUrl("http://localhost:5173/success?session_id={CHECKOUT_SESSION_ID}")
                        .setCancelUrl("http://localhost:5173/cancel")
                        .build();

        Session session = Session.create(params);
      //  log.info(session);
        return session.getUrl();

    }

    public Session getCheckoutSession (String sessionId) throws StripeException {
        return Session.retrieve(sessionId);
    }
    public Customer getCustomer(String customerId) throws StripeException {
        return Customer.retrieve(customerId);
    }
    public List<Subscription> getCustomerSubscriptions(String customerId) throws StripeException {
        Map<String, Object> params = new HashMap<>();
        params.put("customer", customerId);

        SubscriptionListParams subscriptionListParams = SubscriptionListParams.builder()
                .setCustomer(customerId)
                .build();

        SubscriptionCollection subscriptions = Subscription.list(subscriptionListParams);

        return subscriptions.getData();
    }
    public void updateCustomerAndPayment(StripeObject stripeObject) throws StripeException {
        Customer customer = Customer.retrieve(stripeObject.get);
        if (customer == null) {
            return;
        }
        try {
            EmsEmployer byUsername = emsEmployerService.getByUsername(customer.getEmail());

            if(byUsername != null){
                emsEmployerService.updateEmployeeRole(idMask.mask(byUsername.getId()), EmsRole.EMPLOYER_PLUS);
                EmsSubscriptionDto emsSubscriptionDto = new EmsSubscriptionDto().builder()

                        .build();
            }
        } catch (EmsCustomException e) {
            throw new RuntimeException(e);
        }
    }
}
