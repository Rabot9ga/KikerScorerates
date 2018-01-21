package ru.rabot9ga.apiObjects.CreateGame;


import lombok.Builder;
import lombok.Data;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import ru.rabot9ga.kikerScorerates.entity.MongoKickerTable;
import ru.rabot9ga.kikerScorerates.entity.MongoPlayer;

@Data
@Builder
@ApiObject(name = "CreateGameRq", group = "CreateGame")
public class CreateGameRq {
    @ApiObjectField(required = true)
    private MongoPlayer attackerTeam1;
    @ApiObjectField(required = true)
    private MongoPlayer attackerTeam2;
    @ApiObjectField
    private MongoPlayer defenderTeam1;
    @ApiObjectField
    private MongoPlayer defenderTeam2;

//    @ApiObjectField(required = true)
//    private MongoKickerTable kickerTable;
    @ApiObjectField(required = true)
    private int scoreTeam1;
    @ApiObjectField(required = true)
    private int scoreTeam2;

}

