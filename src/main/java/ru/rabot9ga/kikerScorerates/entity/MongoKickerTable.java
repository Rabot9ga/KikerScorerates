package ru.rabot9ga.kikerScorerates.entity;

import lombok.Builder;
import lombok.Data;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.springframework.data.annotation.Id;

@Data
@Builder
@ApiObject(name = "MongoKickerTable")
public class MongoKickerTable {

    @Id
    @ApiObjectField
    private String id;

    @ApiObjectField
    private String name;
}
