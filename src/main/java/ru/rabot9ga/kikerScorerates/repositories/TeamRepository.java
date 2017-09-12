package ru.rabot9ga.kikerScorerates.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.rabot9ga.kikerScorerates.entity.MongoTeam;

import java.util.List;

public interface TeamRepository extends MongoRepository<MongoTeam, String> {

    List<MongoTeam> findByNameLike(String name);

    MongoTeam findByName(String name);

}
