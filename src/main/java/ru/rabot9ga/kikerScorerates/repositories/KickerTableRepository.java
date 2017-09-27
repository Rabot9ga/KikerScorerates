package ru.rabot9ga.kikerScorerates.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.rabot9ga.kikerScorerates.entity.MongoKickerTable;

public interface KickerTableRepository extends MongoRepository<MongoKickerTable, String> {
    MongoKickerTable findByName(String name);
}
