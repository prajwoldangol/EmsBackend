package com.prajwol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmsPhoneVerifyDto {
    @JsonProperty("valid")
    private boolean valid;
    @JsonProperty("sms_email")
    private String sms_email;
    @JsonProperty("sms_domain")
    private String sms_domain;
}
