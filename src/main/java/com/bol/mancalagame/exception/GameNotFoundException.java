package com.bol.mancalagame.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Exception class for game not found case
 */
@Getter
@AllArgsConstructor
public class GameNotFoundException extends RuntimeException {

    private String errorCode;
    private String message;

}
