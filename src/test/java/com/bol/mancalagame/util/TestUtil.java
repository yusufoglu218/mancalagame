package com.bol.mancalagame.util;

import com.bol.mancalagame.constant.TestConstants;
import com.bol.mancalagame.model.Game;
import com.bol.mancalagame.model.Pit;
import com.bol.mancalagame.model.PlayerTurn;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static Game createGameMockOnStart(String gameId) {

        List<Pit> pitsOnStart = new ArrayList<>();
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT1, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT2, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT3, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT4, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT5, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT6, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_TREASURE, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT1, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT2, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT3, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT4, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT5, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT6, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_TREASURE, TestConstants.EMPTY_PIT));

        return Game.builder().id(gameId).playerTurn(TestConstants.PLAYER_TURN_ON_START).winner(PlayerTurn.NO_WINNER).pits(pitsOnStart).build();

    }

    public static Game createGameMockMadeMove(String gameId) {

        List<Pit> pitsOnStart = new ArrayList<>();
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT1, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT2, TestConstants.NUMBER_OF_STONES_ON_START + 1));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT3, TestConstants.NUMBER_OF_STONES_ON_START + 1));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT4, TestConstants.NUMBER_OF_STONES_ON_START + 1));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT5, TestConstants.NUMBER_OF_STONES_ON_START + 1));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT6, TestConstants.NUMBER_OF_STONES_ON_START + 1));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_TREASURE, TestConstants.EMPTY_PIT + 1));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT1, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT2, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT3, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT4, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT5, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT6, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_TREASURE, TestConstants.EMPTY_PIT));

        return Game.builder().id(gameId).playerTurn(TestConstants.PLAYER_TURN_ON_START).winner(PlayerTurn.NO_WINNER).pits(pitsOnStart).currentPitId(TestConstants.PLAYER_A_TREASURE).build();

    }

    public static Game createGameMockForGameOverForPlayerA(String gameId) {

        List<Pit> pitsOnStart = new ArrayList<>();
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT1, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT2, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT3, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT4, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT5, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT6, 1));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_TREASURE, 41));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT1, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT2, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT3, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT4, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT5, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT6, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_TREASURE, TestConstants.EMPTY_PIT));

        return Game.builder().id(gameId).playerTurn(TestConstants.PLAYER_TURN_ON_START).winner(PlayerTurn.NO_WINNER).pits(pitsOnStart).currentPitId(TestConstants.PLAYER_A_TREASURE).build();
    }

    public static Game createGameMockForGameOverForPlayerB(String gameId) {

        List<Pit> pitsOnStart = new ArrayList<>();
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT1, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT2, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT3, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT4, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT5, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT6, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_TREASURE, 6));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT1, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT2, 1));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT3, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT4, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT5, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT6, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_TREASURE, 41));

        return Game.builder().id(gameId).playerTurn(PlayerTurn.PLAYER_B).winner(PlayerTurn.NO_WINNER).pits(pitsOnStart).currentPitId(TestConstants.PLAYER_A_TREASURE).build();
    }

    public static Game createGameMockToCaptureOppositeSit(String gameId) {

        List<Pit> pitsOnStart = new ArrayList<>();
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT1, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT2, TestConstants.NUMBER_OF_STONES_ON_START-3));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT3, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT4, 1));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT5, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_PIT6, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_A_TREASURE, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT1, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT2, TestConstants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT3, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT4, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT5, TestConstants.NUMBER_OF_STONES_ON_START+3));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_PIT6, TestConstants.EMPTY_PIT));
        pitsOnStart.add(new Pit(TestConstants.PLAYER_B_TREASURE, TestConstants.EMPTY_PIT));

        return Game.builder().id(gameId).playerTurn(TestConstants.PLAYER_TURN_ON_START).winner(PlayerTurn.NO_WINNER).pits(pitsOnStart).currentPitId(TestConstants.PLAYER_A_TREASURE).build();
    }

}
