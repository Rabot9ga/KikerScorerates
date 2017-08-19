package ru.rabot9ga.KikerScorerates.entity;

import java.util.concurrent.atomic.AtomicLong;

public class User {
    private long id;
    private String name;
    private String surname;

    public User(String name, String surname, long id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }
}
