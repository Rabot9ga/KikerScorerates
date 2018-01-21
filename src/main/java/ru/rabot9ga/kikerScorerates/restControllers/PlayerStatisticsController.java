package ru.rabot9ga.kikerScorerates.restControllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayerStatistics;
import ru.rabot9ga.kikerScorerates.repositories.PlayerStatisticsRepository;

import java.util.List;

@RestController
@RequestMapping("/stat")
@Slf4j
public class PlayerStatisticsController {

    @Autowired
    PlayerStatisticsRepository playerStatisticsRepository;

    @RequestMapping("/getPlayersRating")
    public List<MongoPlayerStatistics> getPlayersRating(){

        return playerStatisticsRepository.findAll(new Sort(Sort.Direction.DESC, "winPercent"));
    }
}
