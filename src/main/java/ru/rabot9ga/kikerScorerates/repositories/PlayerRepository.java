package ru.rabot9ga.kikerScorerates.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayer;

public interface PlayerRepository extends MongoRepository<MongoPlayer, String> {
    MongoPlayer findByName(String name);
}
