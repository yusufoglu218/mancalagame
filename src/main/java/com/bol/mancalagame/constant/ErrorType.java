package com.bol.mancalagame.constant;

/**
 * Enums for error description and internal error code
 */
public enum ErrorType {
    GAME_NOT_FOUND("Error-001", "Game Not Found with Id: "),
    INVALID_PIT_ID("Error-002",  "Invalid PitId: "),
    PIT_NOT_BELONG_TO_PLAYER("Error-003",  "Current player can not make move with this pitId: ");

    private final String code;
    private final String description;

    ErrorType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }

}
