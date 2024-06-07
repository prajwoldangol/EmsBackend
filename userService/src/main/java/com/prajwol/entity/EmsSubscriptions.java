package com.prajwol.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class EmsSubscriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Instant paidOn;
    private Instant expiringOn;
    private String payCycle;
    private String paymentMethod;
    private Integer paymentAmount;
    private String stripeCustomerId;
    private String stripePayIntentId;
    private String stripeInvoiceId;
    private String stripeLatestCharge;
    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private EmsEmployer emsSubscriber;
}
