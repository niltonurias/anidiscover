package io.github.niltonurias.anidiscover.infrastructure.base;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public interface BaseAssembler<RESOURCE extends BaseResource<RESOURCE>, ENTITY> {
    RESOURCE toResource(ENTITY entity);
    ENTITY toEntity(RESOURCE resource);

    default List<RESOURCE> toResource(List<ENTITY> entities) {
        return entities.stream().map(this::toResource).toList();
    }

    default List<ENTITY> toEntity(List<RESOURCE> resources) {
        return resources.stream().map(this::toEntity).toList();
    }

    default void addSelfLink(Class<?> controller, RESOURCE resource) {
        resource.add(linkTo(controller).slash(resource.getObjectId()).withSelfRel());
    }
}
