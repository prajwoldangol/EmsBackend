package com.prajwol.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmsSubscriptions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime paidOn;
    private LocalDateTime expiringOn;
    private String payCycle;
    private String paymentMethod;
    private BigDecimal paymentAmount;
    @ManyToOne
    @JoinColumn(name = "subscriber_id")
    private EmsEmployer emsSubscriber;
}
