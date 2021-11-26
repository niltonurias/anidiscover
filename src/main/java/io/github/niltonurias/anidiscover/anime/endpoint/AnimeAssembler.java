package io.github.niltonurias.anidiscover.anime.endpoint;

import io.github.niltonurias.anidiscover.anime.domain.AnimeEntity;
import io.github.niltonurias.anidiscover.genre.endpoint.GenreAssembler;
import io.github.niltonurias.anidiscover.infrastructure.base.BaseAssembler;
import org.springframework.stereotype.Component;

@Component
public class AnimeAssembler implements BaseAssembler<AnimeResource, AnimeEntity> {
    private final GenreAssembler genreAssembler;

    public AnimeAssembler(final GenreAssembler genreAssembler) {
        this.genreAssembler = genreAssembler;
    }

    @Override
    public AnimeResource toResource(AnimeEntity resource) {
        return AnimeResource
                .builder()
                    .id(resource.getId())
                    .status(resource.getStatus())
                    .bannerImage(resource.getBannerImage())
                    .coverImage(resource.getCoverImage())
                    .description(resource.getDescription())
                    .duration(resource.getDuration())
                    .genres(genreAssembler.toResource(resource.getGenres()))
                    .season(resource.getSeason())
                    .seasonYear(resource.getSeasonYear())
                    .title(resource.getTitle())
                .build();
    }

    @Override
    public AnimeEntity toEntity(AnimeResource entity) {
        return AnimeEntity
                .builder()
                    .id(entity.getObjectId())
                    .status(entity.getStatus())
                    .bannerImage(entity.getBannerImage())
                    .coverImage(entity.getCoverImage())
                    .description(entity.getDescription())
                    .duration(entity.getDuration())
                    .genres(genreAssembler.toEntity(entity.getGenres()))
                    .season(entity.getSeason())
                    .seasonYear(entity.getSeasonYear())
                    .title(entity.getTitle())
                .build();
    }
}
