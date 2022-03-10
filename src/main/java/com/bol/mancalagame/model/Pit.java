package com.bol.mancalagame.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Class for pit of games
 */
@Data
@AllArgsConstructor
public class Pit {

    private int id;
    private int numberOfStones;

    public void addStone(int i) {
        this.numberOfStones += i;
    }

}
