package ru.rabot9ga.kikerScorerates.entity;

import lombok.Builder;
import lombok.Data;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@ApiObject(name = "MongoPlayer")
@Data
@Builder
@Document
public class MongoPlayer {
    @Id
    @ApiObjectField(description = "Id in mongo DB")
    private String id;

    @Indexed(unique = true)
    @ApiObjectField
    private String name;
}
