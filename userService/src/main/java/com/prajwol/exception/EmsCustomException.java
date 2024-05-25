package com.prajwol.exception;

import lombok.Data;

@Data
public class EmsCustomException  extends Exception{
    private String errorCode;

    public EmsCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
