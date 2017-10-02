package ru.rabot9ga.kikerScorerates.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@Builder
public class MongoPlayerStatistics {

    @Id
    private String id;

    @DBRef
    private MongoPlayer player;

    private double winPercent;
    private long winGames;
    private long looseGames;
    private long allGames;

    public void incrementWinGames(){
        this.winGames++;
        calcOther();
    }

    public void incrementLooseGames(){
        this.looseGames++;
        calcOther();
    }

    private void calcOther() {
        allGames = winGames + looseGames;
        winPercent = (((double) winGames) / (double) allGames) * 100;
    }
}
