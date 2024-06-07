package com.prajwol.service;

import at.favre.lib.idmask.IdMask;
import com.prajwol.dto.EmsSubscriptionDto;
import com.prajwol.entity.EmsSubscriptions;
import com.prajwol.exception.EmsCustomException;
import com.prajwol.repository.EmsSubscriptionsRepo;
import com.prajwol.userservice.IdObfuscationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class EmsSubscriptionServiceImpl implements EmsSubscriptionService {
    private EmsEmployerService emsEmployerService;
    private EmsSubscriptionsRepo emsSubscriptionsRepo;
    private IdObfuscationService idObfuscationService;
    private IdMask<Long> idMask ;
    @Autowired
    public EmsSubscriptionServiceImpl(EmsSubscriptionsRepo emsSubscriptionsRepo,EmsEmployerService emsEmployerService, IdObfuscationService idObfuscationService) {
        this.emsSubscriptionsRepo = emsSubscriptionsRepo;
        this.idObfuscationService = idObfuscationService;
        this.idMask = idObfuscationService.idMask();
        this.emsEmployerService = emsEmployerService;
    }
    @Override
    public EmsSubscriptions getSubscriptionById(String id) throws EmsCustomException {
        Long subId = idMask.unmask(id);

        return emsSubscriptionsRepo.findById(subId)
                .orElseThrow(() -> new EmsCustomException("That Subscription not found", "404"));
    }

    @Override
    public List<EmsSubscriptions> getAllSubscriptions() {
        return List.of();
    }

    @Override
    public EmsSubscriptions addSubscription(EmsSubscriptionDto subscription) {
        EmsSubscriptions sub = new EmsSubscriptions().builder()
                .paidOn(Instant.now())
                .expiringOn(Instant.now().plus(Duration.ofDays(30)))
                .paymentAmount(subscription.getPaymentAmount())
                .payCycle(subscription.getPayCycle())
                .stripeSubscriptionId(subscription.getStripeSubscriptionId())
                .stripeCustomerId(subscription.getStripeCustomerId())
                .stripeInvoiceId(subscription.getStripeInvoiceId())
//                .stripeLatestCharge(subscription.getStripeLatestCharge())
//                .stripePayIntentId(subscription.getStripePayIntentId())
                .build();
        if( subscription.getEmsEmployer() != null){
            Long id = idMask.unmask(subscription.getEmsEmployer());
            emsEmployerService.getById(id)
                    .ifPresent(sub::setEmsEmployer);
        }
        return emsSubscriptionsRepo.save(sub);

    }

    @Override
    public void deleteSubscriptionById(String id) {
        Long subId = idMask.unmask(id);
        emsSubscriptionsRepo.deleteById(subId);
    }
}
