package com.prajwol.entity;

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
public class EmsSubscriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Instant paidOn;
    private Instant expiringOn;
    private String payCycle;
    private String paymentMethod;
    private Integer paymentAmount;
    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private EmsEmployer emsSubscriber;
}
