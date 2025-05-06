package com.hiring.statusms.domain.entity;

import com.hiring.statusms.domain.enums.StatusType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "hiring_status")
public class Status {
    @Id
    private UUID id;

    private StatusType statusType;

    public Status() {
        this.id = UUID.randomUUID();
    }
}
