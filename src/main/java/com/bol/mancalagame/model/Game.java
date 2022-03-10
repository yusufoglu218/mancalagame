package com.bol.mancalagame.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Class for game
 */
@Data
@Builder
@Document
public class Game {

    @Id
    private String id;
    private List<Pit> pits;
    private PlayerTurn playerTurn;
    private PlayerTurn winner;
    private int currentPitId;

}
