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
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayer;
import ru.rabot9ga.kikerScorerates.repositories.PlayerRepository;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PlayerControllerTest {


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
        System.out.println("PlayerControllerTest.setUp");

        playerRepository.save(MongoPlayer.builder().name("Вася").build());
        playerRepository.save(MongoPlayer.builder().name("Петя").build());
        playerRepository.save(MongoPlayer.builder().name("Коля").build());
        playerRepository.save(MongoPlayer.builder().name("Player").build());
        List<MongoPlayer> all = playerRepository.findAll();
        all.forEach(mongoPlayer -> System.out.println("mongoPlayer = " + mongoPlayer));
        System.out.println("--------------------------------------------");
    }

    @Test
    public void getAllPlayers() throws Exception {
        ResultActions actions = mockMvc.perform(get("/getAllPlayers"));
        actions.andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", Matchers.hasSize(4)))
                .andExpect(jsonPath("$[0].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].name", Matchers.is("Вася")))
                .andExpect(jsonPath("$[1].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].name", Matchers.equalToIgnoringCase("Петя")))
                .andExpect(jsonPath("$[2].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$[2].name", Matchers.equalToIgnoringCase("Коля")))
                .andExpect(jsonPath("$[3].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$[3].name", Matchers.equalToIgnoringCase("Player")));

    }

    @Test
    public void getPlayer() throws Exception {
        ResultActions result = mockMvc.perform(get("/getPlayer" + "?name=Вася"));
        result.andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.name", Matchers.is("Вася")));
    }

    @Test
    public void getPlayers() throws Exception {
        String names = json(Arrays.asList("Вася", "Player"));
        ResultActions result = mockMvc.perform(post("/getPlayersByNames")
                .content(names)
                .contentType(contentType));
        result.andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].name", Matchers.is("Вася")))
                .andExpect(jsonPath("$[1].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].name", Matchers.equalToIgnoringCase("Player")));


    }

    @Test
    public void createPlayer() throws Exception {
        ResultActions result = mockMvc.perform(put("/createPlayer?name=Player4").contentType(contentType));
        MongoPlayer mongoPlayer = playerRepository.findByName("Player4");
        System.out.println("mongoPlayer = " + mongoPlayer);
        result.andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.name", Matchers.is("Player4")));
    }

    @Test
    public void createPlayers() throws Exception {
        String json = json(Arrays.asList("Федор", "Player35"));
        System.out.println("json = " + json);
        ResultActions result = mockMvc.perform(put("/createPlayers")
                .contentType(contentType)
                .content(json));
        result.andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$[0].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$[0].name", Matchers.is("Федор")))
                .andExpect(jsonPath("$[1].id", Matchers.notNullValue()))
                .andExpect(jsonPath("$[1].name", Matchers.is("Player35")));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}