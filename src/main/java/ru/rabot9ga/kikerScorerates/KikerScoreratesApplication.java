package ru.rabot9ga.kikerScorerates;

import org.jsondoc.spring.boot.starter.EnableJSONDoc;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJSONDoc
public class KikerScoreratesApplication {

	public static void main(String[] args) {
		SpringApplication.run(KikerScoreratesApplication.class, args);
	}


}
