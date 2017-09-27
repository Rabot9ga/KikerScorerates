//package ru.rabot9ga.kikerScorerates.logic;
//
//import org.jsondoc.core.annotation.ApiObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import ru.rabot9ga.apiObjects.CreateGame.CreateGameRq;
//import ru.rabot9ga.apiObjects.CreateGame.CreateGameRs;
//import ru.rabot9ga.apiObjects.commons.Player;
//import ru.rabot9ga.apiObjects.commons.Role;
//import ru.rabot9ga.apiObjects.commons.Status;
//import ru.rabot9ga.apiObjects.commons.Team;
//import ru.rabot9ga.kikerScorerates.entity.MongoGame;
//import ru.rabot9ga.kikerScorerates.entity.MongoPlayer;
//import ru.rabot9ga.kikerScorerates.entity.MongoTeam;
//import ru.rabot9ga.kikerScorerates.repositories.GameRepository;
//import ru.rabot9ga.kikerScorerates.repositories.PlayerRepository;
//import ru.rabot9ga.kikerScorerates.repositories.TeamRepository;
//import ru.rabot9ga.kikerScorerates.repositories.UserRepository;
//
//import java.util.*;
//import java.util.regex.Pattern;
//import java.util.stream.Collectors;
//import java.util.stream.StreamSupport;
//
//@ApiObject
//public class GameCreator {
//    @Autowired
//    public UserRepository userRepository;
//    @Autowired
//    public GameRepository gameRepository;
//    @Autowired
//    public TeamRepository teamRepository;
//    @Autowired
//    public PlayerRepository playerRepository;
//
//    private CreateGameRq createGameRq;
//    private CreateGameRs createGameRs;
//    private MongoGame mongoGame;
//
//    private Map<String, MongoPlayer> mapNameMongoPlayer;
//    private Map<String, MongoPlayer> mapIDMongoPlayer;
//
//    Map<String, MongoTeam> mapNameMongoTeam;
//    Map<String, MongoTeam> mapIDMongoTeam;
//
//    public GameCreator(CreateGameRq createGameRq) {
//        this.createGameRq = createGameRq;
//        this.createGameRs = new CreateGameRs();
//        this.mongoGame = new MongoGame();
//    }
//
//    public CreateGameRs createGame(){
//        createGameRs = isValid(createGameRq);
//        if (createGameRs.getStatus() != null && createGameRs.getStatus().equals(Status.ERROR)) {
//            return createGameRs;
//        }
//
//        createEmptyPlayers();
//        if (createGameRs.getStatus() != null && createGameRs.getStatus().equals(Status.ERROR)) {
//            return createGameRs;
//        }
//        createEmptyTeams();
//
//        return new CreateGameRs();
//    }
//
//
//    private CreateGameRs isValid(CreateGameRq createGameRq) {
//        List<Team> teams = createGameRq.getTeams();
//        if (teams == null || teams.size() != 2) {
//            return new CreateGameRs().setUuid(createGameRq.getUuid())
//                    .setStatus(Status.ERROR)
//                    .setStatusMessage("Incorrect count of teams");
//        }
//
//        if (teams.get(0).getName() != null || teams.get(1).getName() != null) {
//            if (teams.get(0).getName().equals(teams.get(1).getName())) {
//                return new CreateGameRs().setUuid(createGameRq.getUuid())
//                        .setStatus(Status.ERROR)
//                        .setStatusMessage("Teams shouldn't have same names");
//            }
//        }
//
//        for (Team team : teams) {
//            if (team.getPlayersByNames() == null || team.getPlayersByNames().size() < 1 || team.getPlayersByNames().size() > 2) {
//                return new CreateGameRs().setUuid(createGameRq.getUuid())
//                        .setStatus(Status.ERROR)
//                        .setStatusMessage("List<Player> is null");
//            }
//            if (team.getPlayersByNames().size() == 2) {
//                boolean defenderBusy = false;
//                boolean attackerBusy = false;
//                for (Player player : team.getPlayersByNames()) {
//                    if (player.getRole() == null) return new CreateGameRs().setUuid(createGameRq.getUuid())
//                            .setStatus(Status.ERROR)
//                            .setStatusMessage("Incorrect roles in Players");
//                    if ((player.getRole().equals(Role.ATTACKER) && attackerBusy) || (player.getRole().equals(Role.DEFENDER) && defenderBusy)) {
//                        return new CreateGameRs().setUuid(createGameRq.getUuid())
//                                .setStatus(Status.ERROR)
//                                .setStatusMessage("Incorrect roles in Players");
//                    }
//                    if (player.getRole().equals(Role.ATTACKER) && !attackerBusy) {
//                        attackerBusy = true;
//                    }
//                    if (player.getRole().equals(Role.DEFENDER) && !defenderBusy) {
//                        defenderBusy = true;
//                    }
//                }
//            }
//            for (Player player : team.getPlayersByNames()) {
//                if (player.getName() == null) {
//                    return new CreateGameRs().setUuid(createGameRq.getUuid())
//                            .setStatus(Status.ERROR)
//                            .setStatusMessage("Player name is null");
//                }
//
//
//                if (!Pattern.matches("^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$", player.getName())) {
//                    return new CreateGameRs().setUuid(createGameRq.getUuid())
//                            .setStatus(Status.ERROR)
//                            .setStatusMessage("Incorrect name Player, regEx=^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$");
//                }
//            }
//        }
//
//
//        int scoreA = createGameRq.getScoreA();
//        int scoreB = createGameRq.getScoreB();
//        if (scoreA < 0 || scoreA > 10) {
//            return new CreateGameRs().setUuid(createGameRq.getUuid())
//                    .setStatus(Status.ERROR)
//                    .setStatusMessage("Incorrect score");
//        }
//        if (scoreB < 0 || scoreB > 10) {
//            return new CreateGameRs().setUuid(createGameRq.getUuid())
//                    .setStatus(Status.ERROR)
//                    .setStatusMessage("Incorrect score");
//        }
//        if (scoreA == 10 && scoreB == 10) {
//            return new CreateGameRs().setUuid(createGameRq.getUuid())
//                    .setStatus(Status.ERROR)
//                    .setStatusMessage("Incorrect score");
//        }
//
//
//        return new CreateGameRs().setUuid(createGameRq.getUuid());
//    }
//
//    private void createEmptyPlayers() {
//        List<Player> players = new ArrayList<>();
//        createGameRq.getTeams().stream().map(Team::getPlayersByNames).forEach(players::addAll);
//
//        // TODO: 05.09.2017 Подумать как запросить всех плееров пачкой
//        List<MongoPlayer> playersInDB = players.stream()
//                .map(player -> playerRepository.findByName(player.getName()))
//                .filter(Objects::nonNull)
//                .collect(Collectors.toList());
//
//        mapNameMongoPlayer = playersInDB.stream()
//                .collect(Collectors.toMap(MongoPlayer::getName, mongoPlayer -> mongoPlayer));
//
//        List<Player> listWrongPlayersID = players.stream()
//                .filter(player -> mapNameMongoPlayer.containsKey(player.getName()))
//                .filter(player -> player.getId() != null)
//                .filter(player -> !mapNameMongoPlayer.get(player.getName()).getId().equals(player.getId()))
//                .collect(Collectors.toList());
//
//        if (listWrongPlayersID.size() > 0){
//            createGameRs
//                    .setStatus(Status.ERROR)
//                    .setStatusMessage("Wrong players IDs: \n" + listWrongPlayersID.toString());
//            return;
//        }
//
//        List<Player> playersToCreate = new ArrayList<>();
//
//        players.stream().filter(player -> !mapNameMongoPlayer.containsKey(player.getName())).forEach(playersToCreate::add);
//
//        List<MongoPlayer> listMongoPlayersToCreate = playersToCreate.stream()
//                .map(player -> new MongoPlayer().setName(player.getName()))
//                .peek(mongoPlayer -> mapNameMongoPlayer.put(mongoPlayer.getName(), mongoPlayer))
//                .collect(Collectors.toList());
//
//        playerRepository.save(listMongoPlayersToCreate);
//
//        players.forEach(player -> player.setId(mapNameMongoPlayer.get(player.getName()).getId()));
//        mapIDMongoPlayer = mapNameMongoPlayer.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toMap(MongoPlayer::getId, o -> o));
//    }
//
//    private void createEmptyTeams(){
//        final String tmpNamePrefixTeam = "Team";
//
//        List<Team> teams = createGameRq.getTeams();
//
//        List<Team> fullNullTeams = teams.stream()
//                .filter(team -> team.getId() == null)
//                .filter(team -> team.getName() == null)
//                .collect(Collectors.toList());
//
//        if (fullNullTeams.size() > 0){
//            Set<String> setTeamNames = teamRepository.findByNameLike(tmpNamePrefixTeam).stream().map(MongoTeam::getName).collect(Collectors.toSet());
//            for (Team team : fullNullTeams) {
//                String nameTeam;
//                int i = 0;
//                while(true){
//                    if (!setTeamNames.contains(tmpNamePrefixTeam + String.valueOf(i))){
//                        nameTeam = tmpNamePrefixTeam + String.valueOf(i);
//                        team.setName(nameTeam);
//                        setTeamNames.add(nameTeam);
//                        break;
//                    }
//                    i++;
//                }
//            }
//        }
//
//        List<Team> nullTeam = teams.stream()
//                .filter(team -> team.getId() == null)
//                .collect(Collectors.toList());
//
//        if (nullTeam.size() > 0) {
//            mapNameMongoTeam = nullTeam.stream()
//                    .map(team -> teamRepository.findByName(team.getName()))
//                    .filter(Objects::nonNull)
//                    .collect(Collectors.toMap(MongoTeam::getName, mongoTeam -> mongoTeam));
//
//            nullTeam.stream()
//                    .filter(team -> mapNameMongoTeam.containsKey(team.getName()))
//                    .forEach(team -> team.setName(mapNameMongoTeam.get(team.getName()).getName()));
//
//            List<MongoTeam> teamToCreate = nullTeam.stream()
//                    .filter(team -> !mapNameMongoTeam.containsKey(team.getName()))
//                    .map(team -> new MongoTeam()
//                            .setName(team.getName())
//                            .setPlayer(mapIDMongoPlayer.get(team.getPlayersByNames().get(0).getId()))
//                            .setPlayer(mapIDMongoPlayer.get(team.getPlayersByNames().get(1).getId()))
//                    )
//                    .peek(mongoTeam -> mapNameMongoTeam.put(mongoTeam.getName(), mongoTeam))
//                    .collect(Collectors.toList());
//
//            teamRepository.save(teamToCreate);
//        }
//
//        List<String> idTeamToVerification = teams.stream()
//                .filter(team -> team.getId() != null)
//                .map(Team::getId)
//                .collect(Collectors.toList());
//
//        Iterable<MongoTeam> teamsFoundedByID = teamRepository.findAll(idTeamToVerification);
//
//        if (!StreamSupport.stream(teamsFoundedByID.spliterator(), false)
//                .allMatch(mongoTeam -> mongoTeam.getId().equals(mapNameMongoTeam.get(mongoTeam.getName())))) {
//
//            createGameRs
//                    .setStatus(Status.ERROR)
//                    .setStatusMessage("Incorrect IDs of team");
//            return;
//        }
//
//        teams.forEach(team -> team.setId(mapNameMongoTeam.get(team.getName()).getId()));
//
//    }
//
//    //    public void createEmptyTeams(CreateGameRq createGameRq) {
////        final String tmpNamePrefixTeam = "Team";
////
////        Map<String, MongoTeam> teamsNamesInDB = null;
////        boolean needGetAllTeamsNames = true;
////        for (Team team : createGameRq.getTeams()) {
////            if (team.getId() == null) {
////                if (needGetAllTeamsNames) {
////                    teamsNamesInDB = getAllTeamsNamesInDB();
////                    needGetAllTeamsNames = false;
////                }
////                if (team.getName() == null) {
////                    String nameTeam;
////                    if (teamsNamesInDB != null) {
//////                        LOGGER.debug("Find all teams with name like {}", "\"" + tmpNamePrefixTeam + "\"");
////                        nameTeam = newNameTeam(teamsNamesInDB, tmpNamePrefixTeam);
////
////                    } else {
////                        nameTeam = tmpNamePrefixTeam + "1";
////                    }
////                    MongoTeam mongoTeam = teamRepository.save(new MongoTeam().setName(nameTeam));
////                    team.setName(mongoTeam.getName()).setId(mongoTeam.getId());
////
////                    if (teamsNamesInDB == null) {
////                        teamsNamesInDB = new HashMap<>();
////                    }
////                    teamsNamesInDB.put(mongoTeam.getName(), mongoTeam);
////                } else {
////                    MongoTeam mongoTeam;
////
////                    if (teamsNamesInDB != null && teamsNamesInDB.size() > 0) {
////                        if (teamsNamesInDB.containsKey(team.getName())) {
////                            mongoTeam = teamsNamesInDB.get(team.getName());
////                            team.setId(mongoTeam.getId());
////                            continue;
////                        }
////                    }
////                    if (teamsNamesInDB == null) {
////                        teamsNamesInDB = new HashMap<>();
////                    }
////                    mongoTeam = teamRepository.save(new MongoTeam().setName(team.getName()));
////                    teamsNamesInDB.put(mongoTeam.getName(), mongoTeam);
////                    team.setId(mongoTeam.getId());
////                }
////            }
////        }
////    }
//}
