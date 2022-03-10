package com.bol.mancalagame.service;

import com.bol.mancalagame.constant.Constants;
import com.bol.mancalagame.constant.ErrorType;
import com.bol.mancalagame.exception.GameApiException;
import com.bol.mancalagame.exception.GameNotFoundException;
import com.bol.mancalagame.model.Game;
import com.bol.mancalagame.model.Pit;
import com.bol.mancalagame.model.PlayerTurn;
import com.bol.mancalagame.repository.MancalaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation class for service layer of game operations.
 */
@Service
@Slf4j
public class MancalaServiceImpl implements MancalaService {

    private MancalaRepository mancalaRepository;

    public MancalaServiceImpl(MancalaRepository mancalaRepository) {
        this.mancalaRepository = mancalaRepository;
    }

    /**
     * Creates game object and save to database with starting variables
     *
     * @return Game
     */
    @Override
    public Game createGame() {
        Game game = buildGameOnStart();

        // save to database
        mancalaRepository.save(game);

        log.info("New game is created with id " + game.getId());

        return game;
    }

    /**
     * Gets existing game object from database.
     *
     * @param gameId
     * @return Game
     */
    @Override
    public Game getGame(String gameId) {
        Game game;
        Optional<Game> gameOptional = mancalaRepository.findById(gameId);

        if (gameOptional.isPresent()) {
            game = gameOptional.get();
            log.info("The game retrieved from database with game id: " + gameId);
        } else {
            log.error("The game not found with gameId: " + gameId);
            throw new GameNotFoundException(ErrorType.GAME_NOT_FOUND.getCode(), ErrorType.GAME_NOT_FOUND.getDescription() + gameId);
        }

        return game;
    }

    /**
     * Makes move with game id and pit id and returns game for next move.
     *
     * @param gameId
     * @param pitId
     * @return Game
     */
    @Override
    public Game makeMove(String gameId, Integer pitId) {
        log.info("User request to make move with game id: " + gameId + " pit id: " + pitId);

        Game game;

        Optional<Game> gameOptional = mancalaRepository.findById(gameId);

        if (gameOptional.isPresent()) {
            game = gameOptional.get();
            log.info("The game retrieved from database with game id: " + gameId + " and pitId: " + pitId);
        } else {
            log.error("The game not found with gameId: " + gameId);
            throw new GameNotFoundException(ErrorType.GAME_NOT_FOUND.getCode(), ErrorType.GAME_NOT_FOUND.getDescription() + gameId);
        }

        // if selected pit does not belong to current player throw exception
        if (!ifPitBelongsToCurrentPlayer(game.getPlayerTurn(), pitId)) {
            log.error("The current player can not make move with the pitId: " + pitId);
            throw new GameApiException(ErrorType.PIT_NOT_BELONG_TO_PLAYER.getCode(), ErrorType.PIT_NOT_BELONG_TO_PLAYER.getDescription() + pitId);
        }

        putStones(game, pitId);

        captureOppositePitStones(game);

        if (checkIsGameOver(game)) {
            findWinner(game);
            log.info("The game is over. GameId: " + gameId + " winner: " + game.getWinner());
        } else {
            checkPlayerTurn(game);
        }

        mancalaRepository.save(game);

        log.info("Move is made for game id: " + gameId + " pit id: " + pitId);
        return game;
    }

    /**
     * Remove stones from selected pit and put them to the next pits.
     *
     * @param game
     * @param pitId
     */
    private void putStones(Game game, Integer pitId) {
        Pit selectedPit = game.getPits().get(pitId);
        int numberOfStone = selectedPit.getNumberOfStones();
        int currentIndex = 0;

        selectedPit.setNumberOfStones(Constants.EMPTY_PIT);

        for (int i = pitId + 1; numberOfStone > 0; i++) {
            currentIndex = i % (Constants.TOTAL_NUMBER_OF_PITS);
            game.setCurrentPitId(currentIndex);

            // if current pit is other player's treasure then skip it
            if (isOppositeTreasure(game)) {
                continue;
            }
            game.getPits().get(currentIndex).addStone(Constants.NUMBER_OF_STONES_REGULAR_MOVE);
            numberOfStone--;
        }
    }

    /**
     * When the last stone lands in a current player's empty pit, the last stone and all stones in the
     * opposite pit (the other player’s pit) are collected in current player's treasure.
     *
     * @param game
     */
    private void captureOppositePitStones(Game game) {
        int currentPitNumberOfStones = game.getPits().get(game.getCurrentPitId()).getNumberOfStones();
        int oppositePitId = Math.abs(Constants.MAX_INDEX_OF_PITS - game.getCurrentPitId());
        int oppositePitNumberOfStones = game.getPits().get(oppositePitId).getNumberOfStones();

        // return if the current pit is a treasure or number of stones not equal one or opposite pit has no stones
        if (game.getCurrentPitId() == Constants.PLAYER_A_TREASURE || game.getCurrentPitId() == Constants.PLAYER_B_TREASURE || currentPitNumberOfStones != 1 || oppositePitNumberOfStones == 0) {
            return;
        }

        // check opposite pit for player A
        if (game.getPlayerTurn() == PlayerTurn.PLAYER_A && game.getCurrentPitId() < Constants.PLAYER_A_TREASURE) {
            game.getPits().get(game.getCurrentPitId()).setNumberOfStones(Constants.EMPTY_PIT);
            game.getPits().get(oppositePitId).setNumberOfStones(Constants.EMPTY_PIT);
            game.getPits().get(Constants.PLAYER_A_TREASURE).addStone(currentPitNumberOfStones + oppositePitNumberOfStones);
        }

        // check opposite pit for player B
        if (game.getPlayerTurn() == PlayerTurn.PLAYER_B && game.getCurrentPitId() > Constants.PLAYER_A_TREASURE) {
            game.getPits().get(game.getCurrentPitId()).setNumberOfStones(Constants.EMPTY_PIT);
            game.getPits().get(oppositePitId).setNumberOfStones(Constants.EMPTY_PIT);
            game.getPits().get(Constants.PLAYER_B_TREASURE).addStone(currentPitNumberOfStones + oppositePitNumberOfStones);
        }
    }

    /**
     * Check if the game is over and collect the stones if it is.
     *
     * @param game
     * @return boolean
     */
    private boolean checkIsGameOver(Game game) {
        int totalNumberOfSTonesOppositePits = 0;
        int currentOppositePitId = 0;

        // check if all pits are empty for player A
        if (game.getPlayerTurn() == PlayerTurn.PLAYER_A) {
            for (int i = Constants.PLAYER_A_PIT1; i < Constants.PLAYER_A_TREASURE; i++) {
                if (game.getPits().get(i).getNumberOfStones() != Constants.EMPTY_PIT) {
                    return false;
                }
                currentOppositePitId = Math.abs(Constants.MAX_INDEX_OF_PITS - i);
                totalNumberOfSTonesOppositePits += game.getPits().get(currentOppositePitId).getNumberOfStones();
            }
            game.getPits().get(Constants.PLAYER_B_TREASURE).addStone(totalNumberOfSTonesOppositePits);
        }

        // check if all pits are empty for player A
        if (game.getPlayerTurn() == PlayerTurn.PLAYER_B) {
            for (int i = Constants.PLAYER_B_PIT1; i < Constants.PLAYER_B_TREASURE; i++) {
                if (game.getPits().get(i).getNumberOfStones() != Constants.EMPTY_PIT) {
                    return false;
                }
                currentOppositePitId = Math.abs(Constants.MAX_INDEX_OF_PITS - i);
                totalNumberOfSTonesOppositePits += game.getPits().get(currentOppositePitId).getNumberOfStones();
            }
            game.getPits().get(Constants.PLAYER_A_TREASURE).addStone(totalNumberOfSTonesOppositePits);
        }

        // remove all stones from pits
        removeStonesForPits(game);

        return true;
    }

    /**
     * Find winner by comparing number of stones in each treasures
     *
     * @param game
     */
    private void findWinner(Game game) {
        if (game.getPits().get(Constants.PLAYER_A_TREASURE).getNumberOfStones() > game.getPits().get(Constants.PLAYER_B_TREASURE).getNumberOfStones()) {
            game.setWinner(PlayerTurn.PLAYER_A);
        } else if (game.getPits().get(Constants.PLAYER_A_TREASURE).getNumberOfStones() < game.getPits().get(Constants.PLAYER_B_TREASURE).getNumberOfStones()) {
            game.setWinner(PlayerTurn.PLAYER_B);
        } else {
            game.setWinner(PlayerTurn.DRAW);
        }
    }

    /**
     * Check player turn and change it if it is needed
     *
     * @param game
     */
    private void checkPlayerTurn(Game game) {

        // if last stone is placed in treasure, then turn is not changed
        if (game.getCurrentPitId() == Constants.PLAYER_A_TREASURE || game.getCurrentPitId() == Constants.PLAYER_B_TREASURE) {
            return;
        }

        switch (game.getPlayerTurn()) {
            case PLAYER_A:
                game.setPlayerTurn(PlayerTurn.PLAYER_B);
                break;
            case PLAYER_B:
                game.setPlayerTurn(PlayerTurn.PLAYER_A);
                break;
        }
    }

    /**
     * Check if the current pit is other player's treasure
     *
     * @param game
     * @return boolean
     */
    private boolean isOppositeTreasure(Game game) {
        if ((game.getPlayerTurn() == PlayerTurn.PLAYER_A && game.getCurrentPitId() == Constants.PLAYER_B_TREASURE) || (game.getPlayerTurn() == PlayerTurn.PLAYER_B && game.getCurrentPitId() == Constants.PLAYER_A_TREASURE)) {
            return true;
        }
        return false;
    }

    /**
     * Remove all stones from the pits.
     *
     * @param game
     */
    private void removeStonesForPits(Game game) {
        game.getPits().forEach(
                i -> {
                    if (Constants.PLAYER_A_TREASURE != i.getId() && Constants.PLAYER_B_TREASURE != i.getId()) {
                        i.setNumberOfStones(Constants.EMPTY_PIT);
                    }
                });
    }

    /**
     * Check if selected pit belongs to current player.
     *
     * @param playerTurn
     * @param selectedPitID
     * @return
     */
    private boolean ifPitBelongsToCurrentPlayer(PlayerTurn playerTurn, int selectedPitID) {
        if (playerTurn == PlayerTurn.PLAYER_A && selectedPitID > Constants.PLAYER_A_PIT6) {
            return false;
        } else if (playerTurn == PlayerTurn.PLAYER_B && selectedPitID < Constants.PLAYER_A_PIT6) {
            return false;
        }
        return true;
    }

    /**
     * Build a new game with the starting variables.
     *
     * @return Game
     */
    private Game buildGameOnStart() {
        List<Pit> pitsOnStart = new ArrayList<>();
        pitsOnStart.add(new Pit(Constants.PLAYER_A_PIT1, Constants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(Constants.PLAYER_A_PIT2, Constants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(Constants.PLAYER_A_PIT3, Constants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(Constants.PLAYER_A_PIT4, Constants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(Constants.PLAYER_A_PIT5, Constants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(Constants.PLAYER_A_PIT6, Constants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(Constants.PLAYER_A_TREASURE, Constants.EMPTY_PIT));
        pitsOnStart.add(new Pit(Constants.PLAYER_B_PIT1, Constants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(Constants.PLAYER_B_PIT2, Constants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(Constants.PLAYER_B_PIT3, Constants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(Constants.PLAYER_B_PIT4, Constants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(Constants.PLAYER_B_PIT5, Constants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(Constants.PLAYER_B_PIT6, Constants.NUMBER_OF_STONES_ON_START));
        pitsOnStart.add(new Pit(Constants.PLAYER_B_TREASURE, Constants.EMPTY_PIT));

        return Game.builder().playerTurn(Constants.PLAYER_TURN_ON_START).pits(pitsOnStart).winner(PlayerTurn.NO_WINNER).build();
    }

}

