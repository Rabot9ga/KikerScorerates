package ru.rabot9ga.KikerScorerates.restControllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rabot9ga.KikerScorerates.entity.User;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TestController {
    private static final String template = "Hello, %s!";
    private static AtomicLong idUser = new AtomicLong();

    @RequestMapping("/greeting")
    public User greeting(
            @RequestParam(value="name", defaultValue="World") String name) {
        return new User(name, "Пупкин", idUser.incrementAndGet());
    }
}
