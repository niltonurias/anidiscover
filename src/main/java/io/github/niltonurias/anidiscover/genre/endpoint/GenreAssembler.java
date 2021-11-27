package io.github.niltonurias.anidiscover.genre.endpoint;

import io.github.niltonurias.anidiscover.genre.domain.GenreEntity;
import io.github.niltonurias.anidiscover.infrastructure.base.BaseAssembler;
import org.springframework.stereotype.Component;

@Component
public class GenreAssembler implements BaseAssembler<GenreResource, GenreEntity> {
    @Override
    public GenreResource toResource(GenreEntity entity) {
        var resource = GenreResource
                .builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();

        addSelfLink(GenreController.class, resource);
        return resource;
    }

    @Override
    public GenreEntity toEntity(GenreResource resource) {
        return GenreEntity
                .builder()
                    .id(resource.getObjectId())
                    .name(resource.getName())
                .build();
    }
}
