package ru.rabot9ga.kikerScorerates.entity;


import commons.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDateTime;
import java.util.*;

public class MongoGame {

    @Id
    private String id;
    private int scoreTeam1;
    private int scoreTeam2;

    @CreatedDate
    private LocalDateTime createdDate = LocalDateTime.now();

    @DBRef
    private List<MongoTeam> mongoTeams;

    private Map<String, Role> mapPlayerIDRole;

    public String getId() {
        return id;
    }

    public MongoGame setId(String id) {
        this.id = id;
        return this;
    }

    public int getScoreTeam1() {
        return scoreTeam1;
    }

    public MongoGame setScoreTeam1(int scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
        return this;
    }

    public int getScoreTeam2() {
        return scoreTeam2;
    }

    public MongoGame setScoreTeam2(int scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
        return this;
    }

    public List<MongoTeam> getMongoTeams() {
        return mongoTeams;
    }

    public MongoGame setMongoTeams(List<MongoTeam> mongoTeams) {
        this.mongoTeams = mongoTeams;
        return this;
    }

    public Map<String, Role> getMapPlayerIDRole() {
        return mapPlayerIDRole;
    }

    public MongoGame setMapPlayerIDRole(Map<String, Role> mapPlayerIDRole) {
        this.mapPlayerIDRole = mapPlayerIDRole;
        return this;
    }

    public MongoGame setPlayerIDRole(String playerID, Role role) {
        if (this.mapPlayerIDRole == null) {
            this.mapPlayerIDRole = new HashMap<>();
        }
        this.mapPlayerIDRole.put(playerID, role);
        return this;
    }

    public MongoGame setMongoTeam(MongoTeam team) {
        if (this.mongoTeams == null) {
            this.mongoTeams = new ArrayList<>();
        }
        this.mongoTeams.add(team);
        return this;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public MongoGame setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MongoGame{");
        sb.append("id='").append(id).append('\'');
        sb.append(", scoreTeam1=").append(scoreTeam1);
        sb.append(", scoreTeam2=").append(scoreTeam2);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", mongoTeams=").append(mongoTeams);
        sb.append(", mapPlayerIDRole=").append(mapPlayerIDRole);
        sb.append('}');
        return sb.toString();
    }
}
