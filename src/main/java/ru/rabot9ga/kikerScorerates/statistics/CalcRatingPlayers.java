package ru.rabot9ga.kikerScorerates.statistics;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rabot9ga.kikerScorerates.entity.MongoGame;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayer;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayerStatistics;
import ru.rabot9ga.kikerScorerates.repositories.GameRepository;
import ru.rabot9ga.kikerScorerates.repositories.PlayerStatisticsRepository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CalcRatingPlayers {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerStatisticsRepository playerStatisticsRepository;

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    private BlockingQueue<MongoGame> workQueue = new ArrayBlockingQueue<>(100);

    public void addGameTOCalcStatistic(MongoGame mongoGame) {
        workQueue.add(mongoGame);
    }

    @PostConstruct
    public void initCalcStat() {
        service.scheduleWithFixedDelay(new Runnable() {

            @Override
            public void run() {
                log.debug("Start calc statistics");
                List<MongoPlayerStatistics> statistics = null;
                Map<String, MongoPlayerStatistics> mapIDPlayerStatistics = null;
                int sizeStatisticMap = 0;
                while (!workQueue.isEmpty()) {
                    log.debug("Work queue is not empty");
                    if (statistics == null) {
                        statistics = playerStatisticsRepository.findAll();

                        statistics.forEach(mongoPlayerStatistics -> log.debug(mongoPlayerStatistics.toString()));

                        mapIDPlayerStatistics = statistics.stream()
                                .collect(Collectors.toMap(o -> o.getPlayer().getId(), o -> o));


                        if (mapIDPlayerStatistics != null) {
                            sizeStatisticMap = mapIDPlayerStatistics.size();
                        }
                    }
                    try {
                        MongoGame mongoGame = workQueue.take();
                        // TODO: 02.10.2017 подумать над незакончеными играми
                        if (mongoGame.getScoreTeam1() == 10 || mongoGame.getScoreTeam2() == 10) {
                            if (mongoGame.getScoreTeam1() > mongoGame.getScoreTeam2()) {
                                calcStatisticsPlayer(mapIDPlayerStatistics, mongoGame.getAttackerTeam1(), true);
                                calcStatisticsPlayer(mapIDPlayerStatistics, mongoGame.getAttackerTeam2(), false);
                                calcStatisticsPlayer(mapIDPlayerStatistics, mongoGame.getDefenderTeam1(), true);
                                calcStatisticsPlayer(mapIDPlayerStatistics, mongoGame.getDefenderTeam2(), false);
                            } else {
                                calcStatisticsPlayer(mapIDPlayerStatistics, mongoGame.getAttackerTeam1(), false);
                                calcStatisticsPlayer(mapIDPlayerStatistics, mongoGame.getAttackerTeam2(), true);
                                calcStatisticsPlayer(mapIDPlayerStatistics, mongoGame.getDefenderTeam1(), false);
                                calcStatisticsPlayer(mapIDPlayerStatistics, mongoGame.getDefenderTeam2(), true);
                            }
                        }
                    } catch (InterruptedException e) {
                        log.error("Interrupted exception", e);
                    }
                }

                if (mapIDPlayerStatistics != null) {
                    List<MongoPlayerStatistics> statisticsToSave = mapIDPlayerStatistics.entrySet().stream()
                            .map(Map.Entry::getValue)
                            .collect(Collectors.toList());
                    playerStatisticsRepository.save(statisticsToSave);
                }

                log.debug("End calc statistics");
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
        }, 10, 1, TimeUnit.SECONDS);
    }


}
