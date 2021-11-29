package io.github.niltonurias.anidiscover.anime.endpoint;

import io.github.niltonurias.anidiscover.enums.SeasonEnum;
import io.github.niltonurias.anidiscover.enums.StatusEnum;
import io.github.niltonurias.anidiscover.genre.endpoint.GenreResource;
import io.github.niltonurias.anidiscover.infrastructure.base.BaseResource;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.server.core.Relation;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Relation(value = "anime", collectionRelation = "animes")
public class AnimeResource extends BaseResource<AnimeResource> {
    private String title;
    private Long seasonYear;
    private String coverImage;
    private String bannerImage;
    private Long duration;
    private String description;
    private StatusEnum status;
    private SeasonEnum season;
    private List<GenreResource> genres;

    @Builder
    public AnimeResource(UUID id, String title, Long seasonYear, String coverImage, String bannerImage, Long duration, String description, StatusEnum status, SeasonEnum season, List<GenreResource> genres) {
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
}
