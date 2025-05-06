package com.hiring.statusms.domain.entity;

import com.hiring.statusms.domain.enums.StatusType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "status")
public class Status {
    @Id
    private UUID id;

    private UUID candidateId;

    private StatusType statusType;

    public Status() {
        this.id = UUID.randomUUID();
    }
}
