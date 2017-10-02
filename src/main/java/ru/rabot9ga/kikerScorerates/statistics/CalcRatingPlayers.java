package ru.rabot9ga.kikerScorerates.statistics;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.rabot9ga.kikerScorerates.statistics.Calcs.CalcRating;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CalcRatingPlayers {

    @Autowired
    private CalcRating calcRating;

    private ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void initCalcStat() {
        service.scheduleWithFixedDelay(calcRating, 10, 1, TimeUnit.SECONDS);
    }


}
