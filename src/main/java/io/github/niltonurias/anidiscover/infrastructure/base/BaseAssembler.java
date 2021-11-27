package io.github.niltonurias.anidiscover.infrastructure.base;

import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public interface BaseAssembler<RESOURCE extends BaseResource<RESOURCE>, ENTITY> {
    RESOURCE toResource(ENTITY entity);
    ENTITY toEntity(RESOURCE resource);

    default List<RESOURCE> toResource(List<ENTITY> entities) {
        if (ObjectUtils.isEmpty(entities)) return null;
        return entities.stream().map(this::toResource).collect(Collectors.toList());
    }

    default List<ENTITY> toEntity(List<RESOURCE> resources) {
        if (ObjectUtils.isEmpty(resources)) return null;
        return resources.stream().map(this::toEntity).collect(Collectors.toList());
    }

    default void addSelfLink(Class<?> controller, RESOURCE resource) {
        resource.add(linkTo(controller).slash(resource.getObjectId()).withSelfRel());
    }
}
