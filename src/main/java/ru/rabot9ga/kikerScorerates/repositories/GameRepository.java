package ru.rabot9ga.kikerScorerates.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.rabot9ga.kikerScorerates.entity.MongoGame;

import java.time.LocalDateTime;
import java.util.List;

public interface GameRepository extends MongoRepository<MongoGame, String> {
    List<MongoGame> findByCreatedDateBetween(LocalDateTime timeStart, LocalDateTime timeStop);
}
