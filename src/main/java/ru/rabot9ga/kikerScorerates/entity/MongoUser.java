package ru.rabot9ga.kikerScorerates.entity;

import org.springframework.data.annotation.Id;

public class MongoUser {

    @Id
    private String id;
    private String name;

    // TODO: 24.08.2017 Захешировать и посолить
    private String password;

    public String getId() {
        return id;
    }

    public MongoUser setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public MongoUser setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public MongoUser setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MongoUser{");
        sb.append("id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
