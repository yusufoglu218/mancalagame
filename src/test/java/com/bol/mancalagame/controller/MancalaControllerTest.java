package com.bol.mancalagame.controller;


import com.bol.mancalagame.constant.ErrorType;
import com.bol.mancalagame.constant.TestConstants;
import com.bol.mancalagame.exception.GameApiException;
import com.bol.mancalagame.exception.GameNotFoundException;
import com.bol.mancalagame.model.Game;
import com.bol.mancalagame.service.MancalaService;
import com.bol.mancalagame.util.TestUtil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MancalaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MancalaService mancalaService;

    @Test
    public void createGame_OK() throws Exception {
        Game game = TestUtil.createGameMockOnStart(TestConstants.GAME_ID);
        when(mancalaService.createGame()).thenReturn(game);

        mockMvc.perform(get("/mancala/create"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(TestConstants.GAME_ID)))
                .andExpect(jsonPath("$.playerTurn", is(TestConstants.PLAYER_TURN_ON_START.name())))
                .andExpect(jsonPath("$.winner", is(TestConstants.WINNER_ON_START.name())))
                .andExpect(jsonPath("$.winner", is(TestConstants.WINNER_ON_START.name())))
                .andExpect(jsonPath("$.currentPitId", is(0)))
                .andExpect(jsonPath("$.pits", hasSize(TestConstants.PLAYER_B_TREASURE + 1)));

        verify(mancalaService, times(1)).createGame();
    }

    @Test
    public void getGame_OK() throws Exception {
        Game game = TestUtil.createGameMockOnStart(TestConstants.GAME_ID);
        when(mancalaService.getGame(TestConstants.GAME_ID)).thenReturn(game);

        mockMvc.perform(get("/mancala/get")
                    .param("gameId", TestConstants.GAME_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(TestConstants.GAME_ID)))
                .andExpect(jsonPath("$.playerTurn", is(TestConstants.PLAYER_TURN_ON_START.name())))
                .andExpect(jsonPath("$.winner", is(TestConstants.WINNER_ON_START.name())))
                .andExpect(jsonPath("$.winner", is(TestConstants.WINNER_ON_START.name())))
                .andExpect(jsonPath("$.currentPitId", is(0)))
                .andExpect(jsonPath("$.pits", hasSize(TestConstants.PLAYER_B_TREASURE + 1)));

        verify(mancalaService, times(1)).getGame(TestConstants.GAME_ID);
    }

    @Test
    public void getGame_GameNotFound() throws Exception {
        when(mancalaService.getGame(TestConstants.GAME_ID))
                .thenThrow(new GameNotFoundException(ErrorType.GAME_NOT_FOUND.getCode(), ErrorType.GAME_NOT_FOUND.getDescription() + TestConstants.GAME_ID));

        mockMvc.perform(get("/mancala/get")
                        .param("gameId", TestConstants.GAME_ID))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("@.message").value(ErrorType.GAME_NOT_FOUND.getDescription() + TestConstants.GAME_ID));

        verify(mancalaService, times(1)).getGame(TestConstants.GAME_ID);
    }

    @Test
    public void makeMove_OK() throws Exception {
        Game game = TestUtil.createGameMockOnStart(TestConstants.GAME_ID);
        when(mancalaService.makeMove(Mockito.anyString(), Mockito.any())).thenReturn(game);

        mockMvc.perform(get("/mancala/move")
                        .param("gameId", TestConstants.GAME_ID)
                        .param("pitId", String.valueOf(TestConstants.PLAYER_A_PIT2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(TestConstants.GAME_ID)))
                .andExpect(jsonPath("$.playerTurn", is(TestConstants.PLAYER_TURN_ON_START.name())))
                .andExpect(jsonPath("$.winner", is(TestConstants.WINNER_ON_START.name())))
                .andExpect(jsonPath("$.winner", is(TestConstants.WINNER_ON_START.name())))
                .andExpect(jsonPath("$.currentPitId", is(0)))
                .andExpect(jsonPath("$.pits", hasSize(TestConstants.PLAYER_B_TREASURE + 1)));

        verify(mancalaService, times(1)).makeMove(TestConstants.GAME_ID, Integer.valueOf(TestConstants.PLAYER_A_PIT2));
    }

    @Test
    public void makeMove_GameNotFound() throws Exception {
        when(mancalaService.makeMove(TestConstants.GAME_ID, Integer.valueOf(TestConstants.PLAYER_A_PIT2)))
                .thenThrow(new GameNotFoundException(ErrorType.GAME_NOT_FOUND.getCode(), ErrorType.GAME_NOT_FOUND.getDescription() + TestConstants.GAME_ID));

        mockMvc.perform(get("/mancala/move")
                        .param("gameId", TestConstants.GAME_ID)
                        .param("pitId", String.valueOf(TestConstants.PLAYER_A_PIT2)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("@.message").value(ErrorType.GAME_NOT_FOUND.getDescription() + TestConstants.GAME_ID));

        verify(mancalaService, times(1)).makeMove(TestConstants.GAME_ID, Integer.valueOf(TestConstants.PLAYER_A_PIT2));
    }

    @Test
    public void makeMove_InvalidPitId() throws Exception {
        mockMvc.perform(get("/mancala/move")
                        .param("gameId", TestConstants.GAME_ID)
                        .param("pitId", String.valueOf(TestConstants.PLAYER_B_TREASURE)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("@.message").value(ErrorType.INVALID_PIT_ID.getDescription() + TestConstants.PLAYER_B_TREASURE));
    }

    @Test
    public void makeMove_SelectedPitNotBelongToCurrentPlayer() throws Exception {
        when(mancalaService.makeMove(TestConstants.GAME_ID, Integer.valueOf(TestConstants.PLAYER_A_PIT2)))
                .thenThrow(new GameApiException(ErrorType.PIT_NOT_BELONG_TO_PLAYER.getCode(), ErrorType.PIT_NOT_BELONG_TO_PLAYER.getDescription() + TestConstants.PLAYER_A_PIT2));

        mockMvc.perform(get("/mancala/move")
                        .param("gameId", TestConstants.GAME_ID)
                        .param("pitId", String.valueOf(TestConstants.PLAYER_A_PIT2)))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("@.message").value(ErrorType.PIT_NOT_BELONG_TO_PLAYER.getDescription() + TestConstants.PLAYER_A_PIT2));

        verify(mancalaService, times(1)).makeMove(TestConstants.GAME_ID, Integer.valueOf(TestConstants.PLAYER_A_PIT2));
    }

    @Test
    public void makeMove_InternalError() throws Exception {
        when(mancalaService.makeMove(TestConstants.GAME_ID, Integer.valueOf(TestConstants.PLAYER_A_PIT2)))
                .thenThrow(new Exception("Internal Error Occurred"));

        mockMvc.perform(get("/mancala/move")
                        .param("gameId", TestConstants.GAME_ID)
                        .param("pitId", String.valueOf(TestConstants.PLAYER_A_PIT2)))
                .andExpect(status().is5xxServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("@.message").value("Internal Error Occurred"));

        verify(mancalaService, times(1)).makeMove(TestConstants.GAME_ID, Integer.valueOf(TestConstants.PLAYER_A_PIT2));
    }

}
