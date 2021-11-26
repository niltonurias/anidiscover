package io.github.niltonurias.anidiscover.infrastructure.base;

import java.util.List;

public interface BaseAssembler<RESOURCE, ENTITY> {
    RESOURCE toResource(ENTITY entity);
    ENTITY toEntity(RESOURCE resource);

    default List<RESOURCE> toResource(List<ENTITY> entities) {
        return entities.stream().map(this::toResource).toList();
    }

    default List<ENTITY> toEntity(List<RESOURCE> resources) {
        return resources.stream().map(this::toEntity).toList();
    }
}
