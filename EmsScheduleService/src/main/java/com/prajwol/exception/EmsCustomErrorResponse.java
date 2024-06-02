package com.prajwol.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmsCustomErrorResponse {
    private String errorCode;
    private String errorMessage;

}
