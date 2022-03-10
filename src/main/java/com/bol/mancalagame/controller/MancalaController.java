package com.bol.mancalagame.controller;

import com.bol.mancalagame.constant.Constants;
import com.bol.mancalagame.constant.ErrorType;
import com.bol.mancalagame.exception.GameApiException;
import com.bol.mancalagame.exception.GameNotFoundException;
import com.bol.mancalagame.model.Game;
import com.bol.mancalagame.service.MancalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for game rest api
 */
@Tag(name = "Mancala-Controller", description = "Mancala Game API")
@Slf4j
@RestController
@RequestMapping("/mancala")
public class MancalaController {

    private final MancalaService mancalaService;

    public MancalaController(MancalaService mancalaService) {
        this.mancalaService = mancalaService;
    }

    @Operation(summary = "Create a new game")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful response with a new game",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Game.class))})})
    @GetMapping("/create")
    public Game createGame() {
        return mancalaService.createGame();
    }


    @Operation(summary = "Get the existing game with game id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful response",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Game.class))}),
    @ApiResponse(responseCode = "404", description = "Game not found",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameNotFoundException.class))})})
    @GetMapping("/get")
    public Game getGame(@Parameter(description = "Id of the game") @RequestParam String gameId) {
        return mancalaService.getGame(gameId);
    }

    @Operation(summary = "Make move with given game id and pit id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation that returns the game after making move",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Game.class))}),
            @ApiResponse(responseCode = "404", description = "Game not found",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameNotFoundException.class))}),
            @ApiResponse(responseCode = "400", description = "Pit id does not belong to the current player",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameNotFoundException.class))}),
            @ApiResponse(responseCode = "404", description = "Invalid pit id",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = GameApiException.class))})})
    @GetMapping("/move")
    public Game makeMove(@Parameter(description = "Id of the game to make move") @RequestParam String gameId, @Parameter(description = "Id of the selected pit") @RequestParam Integer pitId) throws Exception {

        // if pitId invalid throws exception
        if (pitId > Constants.PLAYER_B_PIT6 || pitId < Constants.PLAYER_A_PIT1 || pitId == Constants.PLAYER_A_TREASURE) {
            log.error("Invalid pit id: " + pitId);
            throw new GameApiException(ErrorType.INVALID_PIT_ID.getCode(), ErrorType.INVALID_PIT_ID.getDescription() + pitId);
        }
        return mancalaService.makeMove(gameId, pitId);
    }
}
