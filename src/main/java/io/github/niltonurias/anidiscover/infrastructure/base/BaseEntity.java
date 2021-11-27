package io.github.niltonurias.anidiscover.infrastructure.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.beans.FeatureDescriptor;
import java.time.ZonedDateTime;
import java.util.Arrays;
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

    public void mergeWith(BaseEntity entity) {
        BeanWrapper src = new BeanWrapperImpl(entity);
        String[] ignorableProperties = Arrays.stream(src.getPropertyDescriptors())
                                                .map(FeatureDescriptor::getName)
                                                .filter(name -> ObjectUtils.isEmpty(src.getPropertyValue(name)))
                                                .toArray(String[]::new);
        BeanUtils.copyProperties(entity, this, ignorableProperties);
    }
}
