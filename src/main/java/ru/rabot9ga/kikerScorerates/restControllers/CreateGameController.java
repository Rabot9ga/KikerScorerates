package ru.rabot9ga.kikerScorerates.restControllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rabot9ga.CreateGame.CreateGameRq;
import ru.rabot9ga.CreateGame.CreateGameRs;
import ru.rabot9ga.kikerScorerates.entity.MongoUser;
import ru.rabot9ga.kikerScorerates.logic.GameCreator;
import ru.rabot9ga.kikerScorerates.repositories.GameRepository;
import ru.rabot9ga.kikerScorerates.repositories.PlayerRepository;
import ru.rabot9ga.kikerScorerates.repositories.TeamRepository;
import ru.rabot9ga.kikerScorerates.repositories.UserRepository;

import java.util.List;


@RestController
public class CreateGameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreateGameController.class);

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;



    // TODO: 23.08.2017 Переписать метод
    @RequestMapping("/greeting")
    public MongoUser greeting(
            @RequestParam(value = "name", defaultValue = "World") String name) {
        return new MongoUser();
    }


    // TODO: 23.08.2017 Переписать метод
    @RequestMapping("/addUser")
    public ResponseEntity addUser(
            @RequestParam(value = "name", defaultValue = "World") String name,
            @RequestParam(value = "surname", defaultValue = "Hello") String surname) {
        mongoOperations.save(new MongoUser());
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/findUser")
    public List<MongoUser> getUserByLastName(
            @RequestParam(value = "lastName", defaultValue = "Hello") String lastName) {

        List<MongoUser> mongoUsers = mongoOperations.find(new Query(Criteria.where("surname").is(lastName)), MongoUser.class);

        return mongoUsers;
    }

    @RequestMapping("/findAllUsers")
    public List<MongoUser> getAllUsers() {
        return userRepository.findAll();
    }



    @RequestMapping(value = "/createGame", method = RequestMethod.POST)
    public CreateGameRs createGame(@RequestBody CreateGameRq createGameRq) {
        GameCreator gameCreator = new GameCreator(createGameRq);
        CreateGameRs createGameRs = gameCreator.createGame();


        return createGameRs;
    }

    @RequestMapping(value = "/createGame", method = RequestMethod.GET)
    public CreateGameRs createGameTest() {
        CreateGameRq createGameRq = new CreateGameRq();
        GameCreator gameCreator = new GameCreator(createGameRq);
        CreateGameRs createGameRs = gameCreator.createGame();


        return createGameRs;
    }
}
