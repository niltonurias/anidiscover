package io.github.niltonurias.anidiscover.anime.domain;

import io.github.niltonurias.anidiscover.enums.SeasonEnum;
import io.github.niltonurias.anidiscover.enums.StatusEnum;
import io.github.niltonurias.anidiscover.genre.domain.GenreEntity;
import io.github.niltonurias.anidiscover.infrastructure.base.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "anime")
public class AnimeEntity extends BaseEntity {
    private String title;
    private Long seasonYear;
    private String coverImage;
    private String bannerImage;
    private Long duration;
    private String description;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    private SeasonEnum season;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "anime_genre",
            joinColumns = @JoinColumn(name = "anime_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private List<GenreEntity> genres;

    @Builder
    public AnimeEntity(UUID id, String title, Long seasonYear, String coverImage, String bannerImage, Long duration, String description, StatusEnum status, SeasonEnum season, List<GenreEntity> genres) {
        super(id);
        this.title = title;
        this.seasonYear = seasonYear;
        this.coverImage = coverImage;
        this.bannerImage = bannerImage;
        this.duration = duration;
        this.description = description;
        this.status = status;
        this.season = season;
        this.genres = genres;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AnimeEntity that = (AnimeEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
