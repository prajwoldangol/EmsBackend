package com.prajwol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmsSubscriptionDto {
    private String payCycle;
    private String paymentMethod;
    private Integer paymentAmount;
    private String emsSubscriberId;
    private String stripeCustomerId;
    private String stripePayIntentId;
    private String stripeInvoiceId;
    private String stripeLatestCharge;
}
