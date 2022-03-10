package com.bol.mancalagame.repository;

import com.bol.mancalagame.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository class for database operations
 */
public interface MancalaRepository extends MongoRepository<Game, String> {
}
