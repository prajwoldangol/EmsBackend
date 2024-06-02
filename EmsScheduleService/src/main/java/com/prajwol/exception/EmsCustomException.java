package com.prajwol.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmsCustomException  extends Exception{
    private String errorCode;

    public EmsCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
