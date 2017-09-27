package ru.rabot9ga.apiObjects.CreateGame;

import lombok.Builder;
import lombok.Data;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import ru.rabot9ga.apiObjects.commons.Status;
import ru.rabot9ga.kikerScorerates.entity.MongoGame;


@ApiObject(name = "CreateGameRs", group = "CreateGame")
@Data
@Builder
public class CreateGameRs {
    @ApiObjectField
    private Status status;
    @ApiObjectField
    private String statusMessage;
    @ApiObjectField
    private MongoGame mongoGame;
}
