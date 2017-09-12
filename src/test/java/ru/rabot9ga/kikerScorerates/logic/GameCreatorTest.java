package ru.rabot9ga.kikerScorerates.logic;

import commons.Player;
import commons.Role;
import commons.Status;
import commons.Team;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rabot9ga.CreateGame.CreateGameRq;
import ru.rabot9ga.CreateGame.CreateGameRs;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayer;
import ru.rabot9ga.kikerScorerates.entity.MongoTeam;
import ru.rabot9ga.kikerScorerates.repositories.GameRepository;
import ru.rabot9ga.kikerScorerates.repositories.PlayerRepository;
import ru.rabot9ga.kikerScorerates.repositories.TeamRepository;
import ru.rabot9ga.kikerScorerates.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@DataMongoTest(excludeAutoConfiguration = EmbeddedMongoAutoConfiguration.class)
public class GameCreatorTest {
    static final Logger LOGGER = LoggerFactory.getLogger(GameCreatorTest.class);

    @Autowired
    UserRepository userRepository;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;

    private GameCreator gameCreator;


    @Before
    public void setUp() throws Exception {

        playerRepository.deleteAll();
        teamRepository.deleteAll();
    }

    @Test
    public void testInvalidRq() throws Exception {
        CreateGameRq createGameRq = new CreateGameRq();
        List<Team> teamList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            teamList.add(new Team());
        }
        createGameRq.setScoreA(12)
                .setScoreB(0)
                .setTeams(teamList);

        CreateGameRs createGameRs = new CreateGameRs();

        gameCreator = new GameCreator(createGameRq);
        createGameRs = gameCreator.createGame();

        LOGGER.debug("Проверка количества команд");
        LOGGER.debug("createGameRq = " + createGameRq.toString());
        LOGGER.debug("createGameRs = " + createGameRs.toString());
        Assert.assertTrue(createGameRs.getStatus().equals(Status.ERROR) && createGameRs.getStatusMessage().equals("Incorrect count of teams"));
        LOGGER.debug("------------------------------------------------------");


        createGameRq = new CreateGameRq();
        createGameRq.setScoreA(10)
                .setScoreB(0);
        gameCreator = new GameCreator(createGameRq);
        createGameRs = gameCreator.createGame();
        LOGGER.debug("Проверка команд на null");
        LOGGER.debug("createGameRq = " + createGameRq.toString());
        LOGGER.debug("createGameRs = " + createGameRs.toString());
        Assert.assertTrue(createGameRs.getStatus().equals(Status.ERROR) && createGameRs.getStatusMessage().equals("Incorrect count of teams"));
        LOGGER.debug("------------------------------------------------------");


        LOGGER.debug("Проверка на плееров на null");
        teamList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            teamList.add(new Team());
        }
        createGameRq = new CreateGameRq();
        createGameRq.setScoreA(10)
                .setScoreB(2)
                .setTeams(teamList);
        gameCreator = new GameCreator(createGameRq);
        createGameRs = gameCreator.createGame();
        LOGGER.debug("createGameRq = " + createGameRq.toString());
        LOGGER.debug("createGameRs = " + createGameRs.toString());
        Assert.assertTrue(createGameRs.getStatus().equals(Status.ERROR) && createGameRs.getStatusMessage().equals("List<Player> is null"));
        LOGGER.debug("------------------------------------------------------");


        LOGGER.debug("Проверка невалидного счета");
        teamList = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        players.add(new Player().setName("ADdfvd123"));
        for (int i = 0; i < 2; i++) {
            teamList.add(new Team().setPlayers(players));
        }
        createGameRq = new CreateGameRq();
        createGameRq.setScoreA(12)
                .setScoreB(2)
                .setTeams(teamList);
        gameCreator = new GameCreator(createGameRq);
        createGameRs = gameCreator.createGame();
        LOGGER.debug("createGameRq = " + createGameRq.toString());
        LOGGER.debug("createGameRs = " + createGameRs.toString());
        Assert.assertTrue(createGameRs.getStatus().equals(Status.ERROR) && createGameRs.getStatusMessage().equals("Incorrect score"));
        LOGGER.debug("------------------------------------------------------");


        LOGGER.debug("Проверка соответсвия плеерам ролей");
        teamList = new ArrayList<>();
        players = new ArrayList<>();
        players.add(new Player());
        players.add(new Player().setRole(Role.DEFENDER));
        for (int i = 0; i < 2; i++) {
            teamList.add(new Team().setPlayers(players));
        }
        createGameRq = new CreateGameRq();
        createGameRq.setScoreA(12)
                .setScoreB(2)
                .setTeams(teamList);
        LOGGER.debug("Если 2 плеера, то у них должны быть роли и они разные");
        gameCreator = new GameCreator(createGameRq);
        createGameRs = gameCreator.createGame();
        LOGGER.debug("createGameRq = " + createGameRq.toString());
        LOGGER.debug("createGameRs = " + createGameRs.toString());
        Assert.assertTrue(createGameRs.getStatus().equals(Status.ERROR) && createGameRs.getStatusMessage().equals("Incorrect roles in Players"));
        LOGGER.debug("------------------------------------------------------");


        LOGGER.debug("Проверка соответсвия плеерам ролей");
        LOGGER.debug("Если 2 плеера, то у них должны быть роли и они разные");
        teamList = new ArrayList<>();
        players = new ArrayList<>();
        players.add(new Player().setRole(Role.DEFENDER));
        players.add(new Player().setRole(Role.DEFENDER));
        for (int i = 0; i < 2; i++) {
            teamList.add(new Team().setPlayers(players));
        }
        createGameRq = new CreateGameRq();
        createGameRq.setScoreA(12)
                .setScoreB(2)
                .setTeams(teamList);
        gameCreator = new GameCreator(createGameRq);
        createGameRs = gameCreator.createGame();
        LOGGER.debug("createGameRq = " + createGameRq.toString());
        LOGGER.debug("createGameRs = " + createGameRs.toString());
        Assert.assertTrue(createGameRs.getStatus().equals(Status.ERROR) && createGameRs.getStatusMessage().equals("Incorrect roles in Players"));
        LOGGER.debug("------------------------------------------------------");


        LOGGER.debug("Проверка имени плеера на null");
        teamList = new ArrayList<>();
        players = new ArrayList<>();
        players.add(new Player().setRole(Role.ATTACKER));
        players.add(new Player().setRole(Role.DEFENDER).setName("321"));
        for (int i = 0; i < 2; i++) {
            teamList.add(new Team().setPlayers(players));
        }
        createGameRq = new CreateGameRq();
        createGameRq.setScoreA(0)
                .setScoreB(2)
                .setTeams(teamList);
        gameCreator = new GameCreator(createGameRq);
        createGameRs = gameCreator.createGame();
        LOGGER.debug("createGameRq = " + createGameRq.toString());
        LOGGER.debug("createGameRs = " + createGameRs.toString());
        Assert.assertTrue(createGameRs.getStatus().equals(Status.ERROR) && createGameRs.getStatusMessage().equals("Player name is null"));
        LOGGER.debug("------------------------------------------------------");

        LOGGER.debug("Проверка имени плеера по регулярке");
        teamList = new ArrayList<>();
        players = new ArrayList<>();
        players.add(new Player().setRole(Role.ATTACKER).setName("321"));
        players.add(new Player().setRole(Role.DEFENDER).setName("sdfbfg"));
        for (int i = 0; i < 2; i++) {
            teamList.add(new Team().setPlayers(players));
        }
        createGameRq = new CreateGameRq();
        createGameRq.setScoreA(0)
                .setScoreB(2)
                .setTeams(teamList);
        gameCreator = new GameCreator(createGameRq);
        createGameRs = gameCreator.createGame();
        LOGGER.debug("createGameRq = " + createGameRq.toString());
        LOGGER.debug("createGameRs = " + createGameRs.toString());
        Assert.assertTrue(createGameRs.getStatus().equals(Status.ERROR) && createGameRs.getStatusMessage().equals("Incorrect name Player, regEx=^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$"));
        LOGGER.debug("------------------------------------------------------");

        LOGGER.debug("Команды в запросе имеют разное имя или null");
        createGameRq =
                new CreateGameRq()
                        .setTeam(new Team()
                                .setName("Team36")
                                .setPlayer(new Player().setName("Player1").setRole(Role.ATTACKER))
                                .setPlayer(new Player().setName("Player2").setRole(Role.DEFENDER)))
                        .setTeam(new Team()
                                .setName("Team36")
                                .setPlayer(new Player().setName("Player3").setRole(Role.ATTACKER))
                                .setPlayer(new Player().setName("Player4").setRole(Role.DEFENDER)))
                        .setScoreA(1)
                        .setScoreB(10);
        gameCreator = new GameCreator(createGameRq);
        createGameRs = gameCreator.createGame();
        LOGGER.debug("createGameRq = " + createGameRq.toString());
        LOGGER.debug("createGameRs = " + createGameRs.toString());
        Assert.assertTrue(createGameRs.getStatus().equals(Status.ERROR) && createGameRs.getStatusMessage().equals("Teams shouldn't have same names"));
        LOGGER.debug("------------------------------------------------------");


    }

    @Test
    public void createEmptyPlayers() throws Exception {
        CreateGameRq createGameRq;
        CreateGameRs createGameRs;

        createGameRq = new CreateGameRq()
                .setTeam(new Team()
                        .setPlayer(new Player().setName("Player1").setRole(Role.ATTACKER))
                        .setPlayer(new Player().setName("Player2").setRole(Role.DEFENDER)))
                .setTeam(new Team()
                        .setPlayer(new Player().setName("Player3").setRole(Role.ATTACKER))
                        .setPlayer(new Player().setName("Player4").setRole(Role.DEFENDER)))
                .setScoreA(1)
                .setScoreB(10);


        LOGGER.debug("Test create empty players");
        LOGGER.debug(createGameRq.toString());

        gameCreator = new GameCreator(createGameRq);
        createGameRs = gameCreator.createGame();

        LOGGER.debug(createGameRq.toString());
        LOGGER.debug(createGameRs.toString());
        Assert.assertTrue(createGameRs.getStatus() == null);
        validateCreateEmptyPlayerTest(createGameRq);
        LOGGER.debug("--------------------------------------------");

        createGameRq =
                new CreateGameRq()
                        .setTeam(new Team()
                                .setPlayer(new Player().setName("Player5").setRole(Role.ATTACKER))
                                .setPlayer(new Player().setName("Player6").setRole(Role.DEFENDER)))
                        .setTeam(new Team()
                                .setPlayer(new Player().setName("Player3").setRole(Role.ATTACKER))
                                .setPlayer(new Player().setName("Player4").setRole(Role.DEFENDER)))
                        .setScoreA(1)
                        .setScoreB(10);

        LOGGER.debug(createGameRq.toString());
        gameCreator = new GameCreator(createGameRq);
        createGameRs = gameCreator.createGame();

        LOGGER.debug(createGameRq.toString());
        LOGGER.debug(createGameRs.toString());
        Assert.assertTrue(createGameRs.getStatus() == null);
        validateCreateEmptyPlayerTest(createGameRq);

        LOGGER.debug("--------------------------------------------");
        createGameRq =
                new CreateGameRq()
                        .setTeam(new Team()
                                .setPlayer(new Player().setName("Player2").setRole(Role.ATTACKER).setId("321456786_sdcsdc"))
                                .setPlayer(new Player().setName("Player6").setRole(Role.DEFENDER)))
                        .setTeam(new Team()
                                .setPlayer(new Player().setName("Player3").setRole(Role.ATTACKER))
                                .setPlayer(new Player().setName("Player4").setRole(Role.DEFENDER)))
                        .setScoreA(1)
                        .setScoreB(10);



        LOGGER.debug(createGameRq.toString());

        gameCreator = new GameCreator(createGameRq);
        createGameRs = gameCreator.createGame();

        LOGGER.debug(createGameRq.toString());
        LOGGER.debug(createGameRs.toString());

        Assert.assertTrue(createGameRs.getStatus() == Status.ERROR);
        Assert.assertTrue(createGameRs.getStatusMessage().contains("Wrong players IDs:"));

        validateCreateEmptyPlayerTest(createGameRq);

        LOGGER.debug("--------------------------------------------");
        createGameRq =
                new CreateGameRq()
                        .setTeam(new Team()
                                .setPlayer(new Player().setName("Player2").setRole(Role.ATTACKER))
                                .setPlayer(new Player().setName("Player6").setRole(Role.DEFENDER)))
                        .setTeam(new Team()
                                .setPlayer(new Player().setName("Player3").setRole(Role.ATTACKER))
                                .setPlayer(new Player().setName("Player4").setRole(Role.DEFENDER)))
                        .setScoreA(1)
                        .setScoreB(10);

        LOGGER.debug(createGameRq.toString());

        gameCreator = new GameCreator(createGameRq);
        createGameRs = gameCreator.createGame();

        LOGGER.debug(createGameRq.toString());
        LOGGER.debug(createGameRs.toString());


        Assert.assertTrue(createGameRs.getStatus() == null);
        validateCreateEmptyPlayerTest(createGameRq);


    }

    //
//    @Test
//    public void createEmptyTeam() throws Exception {
//        LOGGER.debug("Test createEmptyTeam()");
//        LOGGER.debug("Create player");
//        LOGGER.debug(playerRepository.save(new MongoPlayer().setName("Player1")).toString());
//        LOGGER.debug("Create player");
//        LOGGER.debug(playerRepository.save(new MongoPlayer().setName("Player2")).toString());
//        LOGGER.debug("Create player");
//        LOGGER.debug(playerRepository.save(new MongoPlayer().setName("Player3")).toString());
//        LOGGER.debug("Create player");
//        LOGGER.debug(playerRepository.save(new MongoPlayer().setName("Player4")).toString());
//
//
//        CreateGameRq createGameRq =
//                new CreateGameRq()
//                        .setTeam(new Team()
//                                .setName("Team38")
//                                .setPlayer(createPlayer(playerRepository.findByName("Player1"), Role.ATTACKER))
//                                .setPlayer(createPlayer(playerRepository.findByName("Player2"), Role.DEFENDER)))
//                        .setTeam(new Team()
//                                .setName("Team28")
//                                .setPlayer(createPlayer(playerRepository.findByName("Player3"), Role.ATTACKER))
//                                .setPlayer(createPlayer(playerRepository.findByName("Player4"), Role.DEFENDER)))
//                        .setScoreA(1)
//                        .setScoreB(10);
//
//        LOGGER.debug(createGameRq.toString());
//        createGameController.createEmptyTeams(createGameRq);
//        LOGGER.debug("------------------------------");
//        LOGGER.debug(createGameRq.toString());
//        validateCreateEmptyTeamTest(createGameRq);
//
//        createGameRq =
//                new CreateGameRq()
//                        .setTeam(new Team()
//                                .setPlayer(createPlayer(playerRepository.findByName("Player1"), Role.ATTACKER))
//                                .setPlayer(createPlayer(playerRepository.findByName("Player2"), Role.DEFENDER)))
//                        .setTeam(new Team()
//                                .setName("Team28")
//                                .setPlayer(createPlayer(playerRepository.findByName("Player3"), Role.ATTACKER))
//                                .setPlayer(createPlayer(playerRepository.findByName("Player4"), Role.DEFENDER)))
//                        .setScoreA(1)
//                        .setScoreB(10);
//
//        LOGGER.debug(createGameRq.toString());
//        createGameController.createEmptyTeams(createGameRq);
//        LOGGER.debug("------------------------------");
//        LOGGER.debug(createGameRq.toString());
//        validateCreateEmptyTeamTest(createGameRq);
//
//        createGameRq =
//                new CreateGameRq()
//                        .setTeam(new Team()
//                                .setPlayer(createPlayer(playerRepository.findByName("Player1"), Role.ATTACKER))
//                                .setPlayer(createPlayer(playerRepository.findByName("Player2"), Role.DEFENDER)))
//                        .setTeam(new Team()
//                                .setPlayer(createPlayer(playerRepository.findByName("Player3"), Role.ATTACKER))
//                                .setPlayer(createPlayer(playerRepository.findByName("Player4"), Role.DEFENDER)))
//                        .setScoreA(1)
//                        .setScoreB(10);
//
//        LOGGER.debug(createGameRq.toString());
//        createGameController.createEmptyTeams(createGameRq);
//        LOGGER.debug("------------------------------");
//        LOGGER.debug(createGameRq.toString());
//        validateCreateEmptyTeamTest(createGameRq);
//    }
//
//    @Test
//    public void createGame() throws Exception {
//
//        LOGGER.debug("Test createGame()");
//        LOGGER.debug("-----------------------------------");
//
//        LOGGER.debug("Create player");
//        LOGGER.debug(playerRepository.save(new MongoPlayer().setName("Player1")).toString());
//        LOGGER.debug("Create player");
//        LOGGER.debug(playerRepository.save(new MongoPlayer().setName("Player2")).toString());
//        LOGGER.debug("Create player");
//        LOGGER.debug(playerRepository.save(new MongoPlayer().setName("Player3")).toString());
//        LOGGER.debug("Create player");
//        LOGGER.debug(playerRepository.save(new MongoPlayer().setName("Player4")).toString());
//
//        CreateGameRq createGameRq =
//                new CreateGameRq()
//                        .setTeam(new Team()
//                                .setPlayer(createPlayer(playerRepository.findByName("Player1"), Role.ATTACKER))
//                                .setPlayer(createPlayer(playerRepository.findByName("Player2"), Role.DEFENDER)))
//                        .setTeam(new Team()
//                                .setPlayer(createPlayer(playerRepository.findByName("Player3"), Role.ATTACKER))
//                                .setPlayer(createPlayer(playerRepository.findByName("Player4"), Role.DEFENDER)))
//                        .setScoreA(1)
//                        .setScoreB(10);
//        LOGGER.debug(createGameRq.toString());
//        LOGGER.debug("Try create game");
//        CreateGameRs createGameRs = createGameController.createGame(createGameRq);
//        LOGGER.debug(createGameRs.toString());
//
//        Assert.assertTrue(createGameRs.getStatus().equals(Status.SUCCESS));
//        Assert.assertNotNull(createGameRs.getIdGame());
//    }
//
//
    private Player createPlayer(MongoPlayer mongoPlayer, Role role) {
        return new Player()
                .setId(mongoPlayer.getId())
                .setRole(role)
                .setName(mongoPlayer.getName());
    }

    private void validateCreateEmptyPlayerTest(CreateGameRq createGameRq) {


        List<MongoPlayer> allPlayers = playerRepository.findAll();


        Assert.assertFalse(allPlayers.stream().anyMatch(mongoPlayer -> {
            int countMatch = 0;
            for (MongoPlayer player : allPlayers) {
                if (player.getName().equals(mongoPlayer.getName())) {
                    countMatch++;
                }
            }
            return countMatch > 1;
        }));

        Assert.assertTrue(createGameRq.toString(), createGameRq.getTeams().get(0).getPlayers().get(0) != null);
        Assert.assertTrue(createGameRq.toString(), createGameRq.getTeams().get(0).getPlayers().get(1) != null);
        Assert.assertTrue(createGameRq.toString(), createGameRq.getTeams().get(1).getPlayers().get(0) != null);
        Assert.assertTrue(createGameRq.toString(), createGameRq.getTeams().get(1).getPlayers().get(1) != null);
    }

    private void validateCreateEmptyTeamTest(CreateGameRq createGameRq) {
        List<MongoTeam> allTeams = teamRepository.findAll();
        try {
            Assert.assertFalse(allTeams.stream().anyMatch(mongoTeam -> {
                int countMatch = 0;
                for (MongoTeam team : allTeams) {
                    if (team.getName().equals(mongoTeam.getName())) {
                        countMatch++;
                    }
                }
                return countMatch > 1;
            }));


            Assert.assertTrue(createGameRq.getTeams().get(0).getId() != null);
            Assert.assertTrue(createGameRq.getTeams().get(0).getName() != null);
            Assert.assertTrue(createGameRq.getTeams().get(1).getId() != null);
            Assert.assertTrue(createGameRq.getTeams().get(1).getName() != null);
        } finally {
            teamRepository.delete(createGameRq.getTeams().get(0).getId());
            teamRepository.delete(createGameRq.getTeams().get(1).getId());
        }
    }


    @Test
    public void testReadTeam() throws Exception {
        playerRepository.save(new MongoPlayer().setName("Player1"));
        playerRepository.save(new MongoPlayer().setName("Player2"));
        playerRepository.save(new MongoPlayer().setName("Player3"));
        playerRepository.save(new MongoPlayer().setName("Player4"));
        playerRepository.save(new MongoPlayer().setName("Player5"));
        playerRepository.save(new MongoPlayer().setName("Player6"));
        List<MongoPlayer> all = playerRepository.findAll();
        teamRepository.save(new MongoTeam().setName("Team1").setPlayer(all.get(0)).setPlayer(all.get(1)));
        teamRepository.save(new MongoTeam().setName("Team2").setPlayer(all.get(1)).setPlayer(all.get(2)));
        teamRepository.save(new MongoTeam().setName("Team3").setPlayer(all.get(3)).setPlayer(all.get(4)));
        teamRepository.save(new MongoTeam().setName("Team4").setPlayer(all.get(4)).setPlayer(all.get(5)));
        teamRepository.save(new MongoTeam().setName("Team5").setPlayer(all.get(5)).setPlayer(all.get(2)));
        teamRepository.save(new MongoTeam().setName("Team6").setPlayer(all.get(1)).setPlayer(all.get(2)));
        List<MongoTeam> mongoTeams = teamRepository.findAll(Example.of(new MongoTeam().setPlayer(all.get(0)).setPlayer(all.get(1))));
        List<MongoTeam> mongoTeams1 = teamRepository.findAll(Example.of(new MongoTeam().setPlayer(all.get(1)).setPlayer(all.get(2))));
        List<MongoTeam> allMongoTeams = teamRepository.findAll();
    }
}