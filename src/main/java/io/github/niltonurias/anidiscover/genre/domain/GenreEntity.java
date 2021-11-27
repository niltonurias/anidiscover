package io.github.niltonurias.anidiscover.genre.domain;

import io.github.niltonurias.anidiscover.infrastructure.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "genre")
public class GenreEntity extends BaseEntity {
    @OrderBy
    private String name;

    @Builder
    public GenreEntity(UUID id, String name) {
        super(id);
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreEntity that = (GenreEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
