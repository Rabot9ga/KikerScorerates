package ru.rabot9ga.kikerScorerates.restControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayer;
import ru.rabot9ga.kikerScorerates.repositories.PlayerRepository;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/player")
@Slf4j
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @RequestMapping("/getAll")
    public List<MongoPlayer> getAllPlayers() {
        return playerRepository.findAll();
    }

    @RequestMapping("/getByID")
    public MongoPlayer getByID(@RequestParam(value = "id") String id) {
        return playerRepository.findOne(id);
    }

    @RequestMapping(value = "/getByNames", method = RequestMethod.GET)
    public List<MongoPlayer> getPlayersByNames(@RequestParam(value = "names") List<String> names) {
        return names.stream().map(s -> playerRepository.findByName(s)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/createPlayer", method = RequestMethod.PUT)
    public MongoPlayer createPlayer(@RequestBody String name){
        MongoPlayer mongoPlayer = MongoPlayer.builder()
                .name(name)
                .build();
        return playerRepository.save(mongoPlayer);
    }

    @RequestMapping(value = "/createPlayers", method = RequestMethod.PUT)
    public List<MongoPlayer> createPlayers(@RequestBody List<String> names){
        List<MongoPlayer> mongoPlayers = names.stream()
                .map(s -> MongoPlayer.builder().name(s).build())
                .collect(Collectors.toList());
        return playerRepository.save(mongoPlayers);
    }



}
