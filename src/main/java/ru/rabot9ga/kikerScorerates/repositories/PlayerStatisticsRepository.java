package ru.rabot9ga.kikerScorerates.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayerStatistics;

public interface PlayerStatisticsRepository extends MongoRepository<MongoPlayerStatistics, String> {
}
