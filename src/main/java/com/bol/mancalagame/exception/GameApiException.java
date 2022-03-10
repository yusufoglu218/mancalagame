package com.bol.mancalagame.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception class for custom game api exceptions
 */
@Getter
@AllArgsConstructor
public class GameApiException extends RuntimeException {

    private String errorCode;
    private String message;

}
