package io.micrommer.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Client extends PanacheEntity {
    public Client(String name, Status status, LocalDateTime createdDate) {
        this.name = name;
        this.status = status;
        this.createdDate = createdDate;
    }

    public Client() {
    }

    public enum Status {
        ACTIVE, DEACTIVATE, BLOCKED
    }

    public String name;
    @Enumerated(EnumType.STRING)
    public Status status;
    public LocalDateTime createdDate;

    @Override
    public String toString() {
        return super.toString() + ":" + "{" +
                "name='" + name + '\'' +
                ", status=" + status +
                ", createdDate=" + createdDate +
                '}';
    }

    public static List<Client> getAllBlockedUserInPeriod(LocalDateTime from, LocalDateTime to) {
        return list("status = :status and createdDate between :from and :to",
                Parameters.with("status", Status.BLOCKED).and("from", from).and("to", to));
    }
}
