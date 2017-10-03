package ru.rabot9ga.kikerScorerates.statistics.Calcs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rabot9ga.kikerScorerates.entity.MongoGame;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayer;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayerStatistics;
import ru.rabot9ga.kikerScorerates.repositories.GameRepository;
import ru.rabot9ga.kikerScorerates.repositories.PlayerStatisticsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CalcRating implements Runnable {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerStatisticsRepository playerStatisticsRepository;


    @Override
    public void run() {
        log.debug("Start calc rating");

        List<MongoGame> allGames = gameRepository.findAll();
        allGames.forEach(mongoGame -> log.trace(mongoGame.toString()));

        List<MongoPlayerStatistics> allStatistic = playerStatisticsRepository.findAll();
        allStatistic.forEach(MongoPlayerStatistics::clearStat);

        Map<String, MongoPlayerStatistics> mapPlayerIDStat = allStatistic.stream()
                .collect(Collectors.toMap(o -> o.getPlayer().getId(), o -> o));

//        filter full game
        allGames.stream()
                .filter(mongoGame -> mongoGame.getScoreTeam1() == 10 || mongoGame.getScoreTeam2() == 10)
                .forEach(mongoGame -> {
                    if (mongoGame.getScoreTeam1() > mongoGame.getScoreTeam2()) {
                        calcStatisticsPlayer(mapPlayerIDStat, mongoGame.getAttackerTeam1(), true);
                        calcStatisticsPlayer(mapPlayerIDStat, mongoGame.getAttackerTeam2(), false);
                        calcStatisticsPlayer(mapPlayerIDStat, mongoGame.getDefenderTeam1(), true);
                        calcStatisticsPlayer(mapPlayerIDStat, mongoGame.getDefenderTeam2(), false);
                    } else {
                        calcStatisticsPlayer(mapPlayerIDStat, mongoGame.getAttackerTeam1(), false);
                        calcStatisticsPlayer(mapPlayerIDStat, mongoGame.getAttackerTeam2(), true);
                        calcStatisticsPlayer(mapPlayerIDStat, mongoGame.getDefenderTeam1(), false);
                        calcStatisticsPlayer(mapPlayerIDStat, mongoGame.getDefenderTeam2(), true);
                    }

                });

        if (mapPlayerIDStat != null) {
            List<MongoPlayerStatistics> statisticsToSave = mapPlayerIDStat.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
            statisticsToSave.forEach(playerStatistics -> log.trace(statisticsToSave.toString()));
            playerStatisticsRepository.save(statisticsToSave);
        }
        log.debug("End calc statistic");
    }

    private void calcStatisticsPlayer(Map<String, MongoPlayerStatistics> mongoPlayerStatisticsMap, MongoPlayer player, boolean isWin) {
        if (player != null) {
            MongoPlayerStatistics playerStatistics = null;
            if (mongoPlayerStatisticsMap != null && mongoPlayerStatisticsMap.containsKey(player.getId())) {
                playerStatistics = mongoPlayerStatisticsMap.get(player.getId());
            } else {
                playerStatistics = MongoPlayerStatistics.builder().player(player).build();
                if (mongoPlayerStatisticsMap == null) {
                    mongoPlayerStatisticsMap = new HashMap<>();
                }
                mongoPlayerStatisticsMap.put(playerStatistics.getPlayer().getId(), playerStatistics);
            }
            if (isWin) {
                playerStatistics.incrementWinGames();
            } else {
                playerStatistics.incrementLooseGames();
            }
        }
    }

}
