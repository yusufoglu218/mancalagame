package com.bol.mancalagame.model;

/**
 * Enums for player turn
 */
public enum PlayerTurn {
    PLAYER_A("A"),
    PLAYER_B("B"),
    NO_WINNER(""),
    DRAW("DRAW");

    private final String playerName;

    PlayerTurn(final String playerName) {
        this.playerName = playerName;
    }

    @Override
    public String toString() {
        return playerName;
    }
}
