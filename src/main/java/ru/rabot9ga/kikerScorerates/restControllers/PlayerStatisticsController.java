package ru.rabot9ga.kikerScorerates.restControllers;

import org.springframework.web.bind.annotation.RequestMapping;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayerStatistics;

import java.util.List;


public class PlayerStatisticsController {



    @RequestMapping("/getPlayersRating")
    public List<MongoPlayerStatistics> getPlayersRating(){
        return null;
    }
}
