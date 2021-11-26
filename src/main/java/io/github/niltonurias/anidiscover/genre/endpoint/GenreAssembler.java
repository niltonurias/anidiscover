package io.github.niltonurias.anidiscover.genre.endpoint;

import io.github.niltonurias.anidiscover.genre.domain.GenreEntity;
import io.github.niltonurias.anidiscover.infrastructure.base.BaseAssembler;
import org.springframework.stereotype.Component;

@Component
public class GenreAssembler implements BaseAssembler<GenreResource, GenreEntity> {
    @Override
    public GenreResource toResource(GenreEntity resource) {
        return GenreResource
                .builder()
                    .id(resource.getId())
                    .name(resource.getName())
                .build();
    }

    @Override
    public GenreEntity toEntity(GenreResource entity) {
        return GenreEntity
                .builder()
                    .id(entity.getObjectId())
                    .name(entity.getName())
                .build();
    }
}
