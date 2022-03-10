package com.bol.mancalagame.constant;

import com.bol.mancalagame.model.PlayerTurn;

/**
 * Constant class for game variables
 */
public final class Constants {

    private Constants() {
    }

    public static final String HOME_TEMPLATE = "gameBoard";

    public static final PlayerTurn PLAYER_TURN_ON_START = PlayerTurn.PLAYER_A;

    public static final int PLAYER_A_PIT1 = 0;
    public static final int PLAYER_A_PIT2 = 1;
    public static final int PLAYER_A_PIT3 = 2;
    public static final int PLAYER_A_PIT4 = 3;
    public static final int PLAYER_A_PIT5 = 4;
    public static final int PLAYER_A_PIT6 = 5;
    public static final int PLAYER_A_TREASURE = 6;
    public static final int PLAYER_B_PIT1 = 7;
    public static final int PLAYER_B_PIT2 = 8;
    public static final int PLAYER_B_PIT3 = 9;
    public static final int PLAYER_B_PIT4 = 10;
    public static final int PLAYER_B_PIT5 = 11;
    public static final int PLAYER_B_PIT6 = 12;
    public static final int PLAYER_B_TREASURE = 13;

    public static final int MAX_INDEX_OF_PITS = 12;
    public static final int TOTAL_NUMBER_OF_PITS = 14;

    public static final int NUMBER_OF_STONES_REGULAR_MOVE = 1;
    public static final int NUMBER_OF_STONES_ON_START = 6;

    public static final int EMPTY_PIT = 0;
}

