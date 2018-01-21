package ru.rabot9ga.eventsListeners;

import org.springframework.context.ApplicationEvent;

public class MongoGameCreateEvent extends ApplicationEvent{


    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MongoGameCreateEvent(Object source) {
        super(source);
    }
}
