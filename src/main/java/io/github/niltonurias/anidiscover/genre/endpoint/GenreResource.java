package io.github.niltonurias.anidiscover.genre.endpoint;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.github.niltonurias.anidiscover.infrastructure.base.BaseResource;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({"id", "name", "links"})
public class GenreResource extends BaseResource<GenreResource> {
    private String name;

    @Builder
    GenreResource(UUID id, String name) {
        super(id);
        this.name = name;
    }
}
