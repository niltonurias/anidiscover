package io.github.niltonurias.anidiscover.infrastructure.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaseResource<RESOURCE extends RepresentationModel<? extends RESOURCE>> extends RepresentationModel<RESOURCE> {
    @JsonProperty(value = "id", index = 1)
    private UUID objectId;
}
