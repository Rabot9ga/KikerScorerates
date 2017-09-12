package ru.rabot9ga.kikerScorerates.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.rabot9ga.kikerScorerates.entity.MongoUser;

import java.util.List;

public interface UserRepository extends MongoRepository<MongoUser, String> {
    List<MongoUser> findAllByNameLike(String name);
}
