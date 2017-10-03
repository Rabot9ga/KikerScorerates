package ru.rabot9ga.kikerScorerates.restControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rabot9ga.apiObjects.CreateGame.CreateGameRq;
import ru.rabot9ga.apiObjects.CreateGame.CreateGameRs;
import ru.rabot9ga.apiObjects.commons.Status;
import ru.rabot9ga.kikerScorerates.entity.MongoGame;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayer;
import ru.rabot9ga.kikerScorerates.repositories.GameRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/game")
@Slf4j
public class GameController {

    @Autowired
    private GameRepository gameRepository;


    @RequestMapping("/getAll")
    public List<MongoGame> getAllGames() {
        return gameRepository.findAll();
    }

    @RequestMapping("/getGameByCreatedDateBetween")
    public List<MongoGame> getGameByCreatedDateBetween(
            @RequestParam(value = "from") LocalDateTime timeFrom,
            @RequestParam(value = "to") LocalDateTime timeTo) {
        return gameRepository.findByCreatedDateBetween(timeFrom, timeTo);
    }

    @RequestMapping("/getByID")
    public MongoGame getByID(@RequestParam(value = "id") String id) {
        return gameRepository.findOne(id);
    }

    // TODO: 02.10.2017 Добавить в запрос параметр время игры (опциональный)
    @RequestMapping(value = "/createGame", method = RequestMethod.PUT)
    public CreateGameRs createGame(@RequestBody CreateGameRq createGameRq) {
        log.debug("/createGame - {}", createGameRq);

//        CreateGameRs createGameRs = validateCreateGameRequest(createGameRq);
//        if (createGameRs.getStatus().equals(Status.ERROR)) {
//            return createGameRs;
//        }

        MongoGame mongoGame = MongoGame.builder()
                .attackerTeam1(createGameRq.getAttackerTeam1())
                .attackerTeam2(createGameRq.getAttackerTeam2())
                .defenderTeam1(createGameRq.getDefenderTeam1())
                .defenderTeam2(createGameRq.getDefenderTeam2())
                .scoreTeam1(createGameRq.getScoreTeam1())
                .scoreTeam2(createGameRq.getScoreTeam2())
                .mongoKickerTable(createGameRq.getKickerTable())
                .build();
        log.debug(mongoGame.toString());

        gameRepository.save(mongoGame);

        if (mongoGame.getId() != null) {

            log.debug("Game created, {}", mongoGame.toString());
            return CreateGameRs.builder()
                    .status(Status.SUCCESS)
                    .mongoGame(mongoGame)
                    .build();
        }
        log.error("Game didn't created");
        return CreateGameRs.builder()
                .status(Status.ERROR)
                .statusMessage("Game didn't created")
                .build();
    }

    private CreateGameRs validateCreateGameRequest(CreateGameRq createGameRq) {
        if (!validatePlayer(createGameRq.getAttackerTeam1())) {
            return CreateGameRs.builder()
                    .status(Status.ERROR)
                    .statusMessage("Incorrect player, attacker1")
                    .build();
        }
        if (!validatePlayer(createGameRq.getAttackerTeam2())) {
            return CreateGameRs.builder()
                    .status(Status.ERROR)
                    .statusMessage("Incorrect player, attacker2")
                    .build();
        }
        if (!validatePlayer(createGameRq.getDefenderTeam1())) {
            return CreateGameRs.builder()
                    .status(Status.ERROR)
                    .statusMessage("Incorrect player, defender1")
                    .build();
        }
        if (!validatePlayer(createGameRq.getDefenderTeam2())) {
            return CreateGameRs.builder()
                    .status(Status.ERROR)
                    .statusMessage("Incorrect player, defender2")
                    .build();
        }
        return CreateGameRs.builder()
                .build();
    }

    private boolean validatePlayer(MongoPlayer mongoPlayer) {
        if (mongoPlayer != null) {
            if (mongoPlayer.getId() == null) {
                return false;
            }
            if (mongoPlayer.getName() == null) {
                return false;
            }
            return true;
        }
        return false;
    }
}
