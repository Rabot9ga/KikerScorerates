package ru.rabot9ga.kikerScorerates.entity;

import lombok.Builder;
import lombok.Data;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@ApiObject(name = "MongoKickerTable")
@Document
public class MongoKickerTable {

    @Id
    @ApiObjectField
    private String id;

    @ApiObjectField
    @Indexed(unique = true)
    private String name;
}
