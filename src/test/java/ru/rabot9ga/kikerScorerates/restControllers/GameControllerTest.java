package ru.rabot9ga.kikerScorerates.restControllers;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import ru.rabot9ga.kikerScorerates.entity.MongoGame;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayer;
import ru.rabot9ga.kikerScorerates.repositories.GameRepository;
import ru.rabot9ga.kikerScorerates.repositories.PlayerRepository;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class GameControllerTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;


    @Autowired
    private WebApplicationContext webApplicationContext;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;


    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }


    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
        System.out.println("GameControllerTest.setUp");
        List<MongoPlayer> mongoPlayers = playerRepository.save(Arrays.asList(
                MongoPlayer.builder().name("Вася").build(),
                MongoPlayer.builder().name("Петя").build(),
                MongoPlayer.builder().name("Коля").build(),
                MongoPlayer.builder().name("Player").build()));


        List<MongoGame> mongoGames = gameRepository.save(Arrays.asList(
                MongoGame.builder()
                        .attackerTeam1(mongoPlayers.get(0))
                        .attackerTeam2(mongoPlayers.get(1))
                        .defenderTeam1(mongoPlayers.get(2))
                        .defenderTeam2(mongoPlayers.get(3))
                        .scoreTeam2(1)
                        .scoreTeam1(10)
                        .build(),
                MongoGame.builder()
                        .attackerTeam1(mongoPlayers.get(0))
                        .attackerTeam2(mongoPlayers.get(1))
                        .defenderTeam1(mongoPlayers.get(2))
                        .defenderTeam2(mongoPlayers.get(3))
                        .scoreTeam2(8)
                        .scoreTeam1(10)
                        .build()));
        mongoGames.forEach(mongoGame -> System.out.println("mongoGame = " + mongoGame));
        System.out.println("--------------------------------------");
    }

    @Test
    public void getAllGames() throws Exception {
        ResultActions result = mockMvc.perform(get("/getAllGames"));
        result.andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    public void getGameByCreatedDateBetween() throws Exception {
    }

    @Test
    public void createGame() throws Exception {
    }

}