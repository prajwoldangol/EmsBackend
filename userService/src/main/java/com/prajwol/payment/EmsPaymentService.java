package com.prajwol.payment;

import at.favre.lib.idmask.IdMask;
import com.prajwol.dto.EmsEmployerResponseDto;
import com.prajwol.dto.EmsSubscriptionDto;
import com.prajwol.entity.EmsEmployer;
import com.prajwol.entity.EmsRole;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.service.EmsEmployerService;
import com.prajwol.service.EmsSubscriptionService;
import com.prajwol.userservice.IdObfuscationService;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
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
    private EmsSubscriptionService emsSubscriptionService;
    private IdObfuscationService idObfuscationService;
    private IdMask<Long> idMask;
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        com.stripe.Stripe.apiKey = stripeApiKey;
    }
    @Autowired
    public EmsPaymentService(EmsEmployerService emsEmployerService,EmsSubscriptionService emsSubscriptionService, IdObfuscationService idObfuscationService) {
        this.emsEmployerService = emsEmployerService;
        this.emsSubscriptionService = emsSubscriptionService;
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
    public Subscription getSubscription(String subscriptionId) throws  StripeException{
       return Subscription.retrieve(subscriptionId);
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
        if(stripeObject == null){
            return ;
        }
//        PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
        Session session = (Session) stripeObject;
        EmsSubscriptionDto subscriptionDto = new EmsSubscriptionDto().builder()
                .stripeSubscriptionId(session.getSubscription())
                .paymentAmount((int) (session.getAmountTotal() / 100))
                .emsEmployer(session.getCustomer())
                .stripeCustomerId(session.getCustomer())
//                .stripePayIntentId(session.getId())
                .stripeInvoiceId(session.getInvoice())
//                .stripeLatestCharge(paymentIntent.getLatestCharge())
                .build();
        EmsEmployer byUsername = null;
        try {
            byUsername = emsEmployerService.getByUsername(session.getCustomerEmail());
        } catch (EmsCustomException e) {
            throw new RuntimeException(e);
        }

        if(byUsername != null){
                EmsEmployerResponseDto emsEmployerResponseDto = null;
                try {
                    Subscription subscription = getSubscription(session.getSubscription());
//                    log.info(subscription);
                    String planId = subscription.getItems().getData().get(0).getPlan().getId();
                    emsEmployerResponseDto = emsEmployerService.updateEmployeeRole(idMask.mask(byUsername.getId()), getRole(planId));
                    subscriptionDto.setEmsEmployer(emsEmployerResponseDto.getId());
                } catch (EmsCustomException e) {
                    throw new RuntimeException(e);
                }
                emsSubscriptionService.addSubscription(subscriptionDto);

            }

    }

    public EmsRole getRole(String roleId) {
        HashMap<String, EmsRole> roles = new HashMap<>();
        roles.put("price_1PNf3GIljL5sgmrY5RuKzvWQ", EmsRole.EMPLOYER_BASIC);
        roles.put("price_1PNf4eIljL5sgmrY3Q9rxBOm", EmsRole.EMPLOYER_STANDARD);
        roles.put("price_1PNf4pIljL5sgmrYrJi5VOt6", EmsRole.EMPLOYER_PLUS);
        if(roles.containsKey(roleId)){
            return roles.get(roleId);
        }
        return EmsRole.USER;
    }
}
