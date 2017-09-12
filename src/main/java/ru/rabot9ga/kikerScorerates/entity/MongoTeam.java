package ru.rabot9ga.kikerScorerates.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.Set;

public class MongoTeam {

    @Id
    private String id;
    private String name;

    @DBRef
    private Set<MongoPlayer> players;


    public String getId() {
        return id;
    }

    public MongoTeam setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MongoTeam setName(String name) {
        this.name = name;
        return this;
    }

    public Set<MongoPlayer> getPlayers() {
        return players;
    }

    public MongoTeam setPlayers(Set<MongoPlayer> players) {
        this.players = players;
        return this;
    }

    public MongoTeam setPlayer(MongoPlayer player){
        if (this.players == null) {
            this.players = new HashSet<>();
        }
        this.players.add(player);
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MongoTeam{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", players=").append(players);
        sb.append('}');
        return sb.toString();
    }
}
