package ru.rabot9ga.kikerScorerates.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.rabot9ga.kikerScorerates.entity.MongoGame;

import java.time.LocalDateTime;

public interface GameRepository extends MongoRepository<MongoGame, String> {
    MongoGame findByCreatedDateBetween(LocalDateTime timeStart, LocalDateTime timeStop);
}
