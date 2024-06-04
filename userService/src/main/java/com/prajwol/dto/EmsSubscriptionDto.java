package com.prajwol.dto;

import com.prajwol.entity.EmsEmployer;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmsSubscriptionDto {
    private String payCycle;
    private String paymentMethod;
    private Integer paymentAmount;
    private String emsSubscriberId;
}
