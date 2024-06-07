package com.prajwol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmsSubscriptionsDto {
    private String id;
    private Instant paidOn;
    private Instant expiringOn;
    private String payCycle;
    private String paymentMethod;
    private Integer paymentAmount;
    private String stripeCustomerId;
    private String stripePayIntentId;
    private String stripeInvoiceId;
    private String stripeLatestCharge;
}
