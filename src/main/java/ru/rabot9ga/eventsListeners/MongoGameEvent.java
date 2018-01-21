package ru.rabot9ga.eventsListeners;

import org.springframework.context.ApplicationEvent;
import ru.rabot9ga.kikerScorerates.entity.MongoGame;

public class MongoGameEvent extends ApplicationEvent {

    private MongoGame mongoGame;


    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MongoGameEvent(Object source, MongoGame mongoGame) {
        super(source);
        this.mongoGame = mongoGame;
    }

    public MongoGame getMongoGame() {
        return mongoGame;
    }
}
