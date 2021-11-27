package io.github.niltonurias.anidiscover.infrastructure.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private ZonedDateTime created;
    private ZonedDateTime updated;

    public BaseEntity(UUID id) {
        this.id = id;
    }

    @PrePersist
    public void prePersisted() {
        this.created = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdated() {
        this.updated = ZonedDateTime.now();
    }
}
