package com.bol.mancalagame.service;

import com.bol.mancalagame.model.Game;

/**
 * Interface for game services.
 */
public interface MancalaService {

    /**
     * Create a new game
     * @return
     */
    Game createGame();

    /**
     * Get the existing game
     * @param gameId
     * @return
     */
    Game getGame(String gameId);

    /**
     * Make move according to rules
     * @param gameId
     * @param pitId
     * @return
     * @throws Exception
     */
    Game makeMove(String gameId,Integer pitId) throws Exception;
}
