package ru.rabot9ga.kikerScorerates.entity;

import org.springframework.data.annotation.Id;

public class MongoPlayer {
    @Id
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public MongoPlayer setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MongoPlayer setName(String name) {
        this.name = name;
        return this;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MongoPlayer{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
