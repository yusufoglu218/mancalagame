package com.bol.mancalagame.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class for building error response
 */
@Getter
@AllArgsConstructor
public class ErrorResponse {

    private String message;
    private String internalErrorCode;

}
