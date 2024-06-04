package com.prajwol.service;

import com.prajwol.dto.EmsSubscriptionDto;
import com.prajwol.entity.EmsSubscriptions;
import com.prajwol.exception.EmsCustomException;

import java.util.List;

public interface EmsSubscriptionService {
    public EmsSubscriptions getSubscriptionById(String id) throws EmsCustomException;
    public List<EmsSubscriptions> getAllSubscriptions();
    public EmsSubscriptions addSubscription(EmsSubscriptionDto subscription);
    public void deleteSubscriptionById(String id);
}
