package com.bol.mancalagame.service;

import com.bol.mancalagame.constant.ErrorType;
import com.bol.mancalagame.constant.TestConstants;
import com.bol.mancalagame.exception.GameApiException;
import com.bol.mancalagame.exception.GameNotFoundException;
import com.bol.mancalagame.model.Game;
import com.bol.mancalagame.model.PlayerTurn;
import com.bol.mancalagame.repository.MancalaRepository;
import com.bol.mancalagame.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MancalaServiceTest {

    @Mock
    MancalaRepository mancalaRepository;

    @InjectMocks
    MancalaServiceImpl mancalaService;

    @Test
    public void createGame_OK() {
        Game gameMock = TestUtil.createGameMockOnStart(null);
        when(mancalaRepository.save(gameMock)).thenReturn(gameMock);

        Game gameFromService = mancalaService.createGame();

        assertNotNull(gameFromService);
        assertNotNull(gameFromService.getPlayerTurn());
        assertNotNull(gameFromService.getWinner());
        assertNotNull(gameFromService.getCurrentPitId());
        assertTrue(gameFromService.getPits().size() > 0);
    }

    @Test
    public void getGame_OK() {
        Game gameMockOnStart = TestUtil.createGameMockOnStart(TestConstants.GAME_ID);
        when(mancalaRepository.findById(gameMockOnStart.getId())).thenReturn(Optional.of(gameMockOnStart));

        Game gameFromService = mancalaService.getGame(TestConstants.GAME_ID);

        assertTrue(gameFromService.equals(gameMockOnStart));
    }

    @Test
    public void getGame_GameNotFound() {
        when(mancalaRepository.findById(TestConstants.GAME_ID)).thenReturn(Optional.empty());

        GameNotFoundException thrown = Assertions.assertThrows(GameNotFoundException.class, () -> {
            mancalaService.getGame(TestConstants.GAME_ID);
        });
        assertEquals(ErrorType.GAME_NOT_FOUND.getDescription() + TestConstants.GAME_ID, thrown.getMessage());
    }

    @Test
    public void makeMove_OK() {
        Game gameMockOnStart = TestUtil.createGameMockOnStart(TestConstants.GAME_ID);
        when(mancalaRepository.findById(gameMockOnStart.getId())).thenReturn(Optional.of(gameMockOnStart));

        Game gameFromService = mancalaService.makeMove(gameMockOnStart.getId(), gameMockOnStart.getPits().get(0).getId());

        Game createGameMockMadeMove = TestUtil.createGameMockMadeMove(TestConstants.GAME_ID);

        assertTrue(gameFromService.equals(createGameMockMadeMove));
    }

    @Test
    public void makeMove_GameNotFound() {
        when(mancalaRepository.findById(TestConstants.GAME_ID)).thenReturn(Optional.empty());

        GameNotFoundException thrown = Assertions.assertThrows(GameNotFoundException.class, () -> {
            mancalaService.makeMove(TestConstants.GAME_ID, TestConstants.PLAYER_A_PIT2);
        });
        assertEquals(ErrorType.GAME_NOT_FOUND.getDescription() + TestConstants.GAME_ID, thrown.getMessage());
    }

    @Test
    public void makeMove_IsGameOverForPlayerA() {
        Game gameMockOnStart = TestUtil.createGameMockForGameOverForPlayerA(TestConstants.GAME_ID);
        when(mancalaRepository.findById(gameMockOnStart.getId())).thenReturn(Optional.of(gameMockOnStart));

        Game gameFromService = mancalaService.makeMove(gameMockOnStart.getId(), gameMockOnStart.getPits().get(5).getId());

        assertTrue(gameFromService.getWinner() == PlayerTurn.PLAYER_A);
    }

    @Test
    public void makeMove_IsGameOverForPlayerB() {
        Game gameMockOnStart = TestUtil.createGameMockForGameOverForPlayerB(TestConstants.GAME_ID);
        when(mancalaRepository.findById(gameMockOnStart.getId())).thenReturn(Optional.of(gameMockOnStart));

        Game gameFromService = mancalaService.makeMove(gameMockOnStart.getId(), gameMockOnStart.getPits().get(TestConstants.PLAYER_B_PIT2).getId());

        assertTrue(gameFromService.getWinner() == PlayerTurn.PLAYER_B);
    }

    @Test
    public void makeMove_IfCaptureOppositePitStones() {
        Game gameMockOnStart = TestUtil.createGameMockToCaptureOppositeSit(TestConstants.GAME_ID);
        when(mancalaRepository.findById(gameMockOnStart.getId())).thenReturn(Optional.of(gameMockOnStart));

        int numberOfStonesToBeCaptured = gameMockOnStart.getPits().get(TestConstants.PLAYER_B_PIT2).getNumberOfStones();

        Game gameFromService = mancalaService.makeMove(gameMockOnStart.getId(), gameMockOnStart.getPits().get(TestConstants.PLAYER_A_PIT4).getId());

        assertTrue(gameFromService.getPits().get(TestConstants.PLAYER_A_TREASURE).getNumberOfStones() == numberOfStonesToBeCaptured + 1);
    }

    @Test
    public void makeMove_IfPitNotBelongToCurrentPlayer() {
        Game gameMock = TestUtil.createGameMockOnStart(TestConstants.GAME_ID);
        when(mancalaRepository.findById(gameMock.getId())).thenReturn(Optional.of(gameMock));

        GameApiException thrown = Assertions.assertThrows(GameApiException.class, () -> {
            mancalaService.makeMove(gameMock.getId(), gameMock.getPits().get(7).getId());
        });

        assertEquals(ErrorType.PIT_NOT_BELONG_TO_PLAYER.getDescription() + gameMock.getPits().get(7).getId(), thrown.getMessage());
    }


}
