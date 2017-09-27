package ru.rabot9ga.kikerScorerates.entity;


import lombok.Builder;
import lombok.Data;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@ApiObject(name = "MongoGame")
public class MongoGame {

    @Id
    @ApiObjectField(order = 1)
    private String id;

    @ApiObjectField(order = 2)
    @CreatedDate
    @Builder.Default
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createdDate = LocalDateTime.now();

    @ApiObjectField(order = 3)
    private int scoreTeam1;
    @ApiObjectField(order = 4)
    private int scoreTeam2;


    @ApiObjectField
    @DBRef
    private MongoPlayer defenderTeam1;

    @ApiObjectField
    @DBRef
    private MongoPlayer defenderTeam2;

    @ApiObjectField
    @DBRef
    private MongoPlayer attackerTeam1;

    @ApiObjectField
    @DBRef
    private MongoPlayer attackerTeam2;

    @ApiObjectField
    @DBRef
    private MongoKickerTable mongoKickerTable;

}
