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
    public AnimeResource toResource(AnimeEntity entity) {
        var resource = AnimeResource
                .builder()
                    .id(entity.getId())
                    .status(entity.getStatus())
                    .bannerImage(entity.getBannerImage())
                    .coverImage(entity.getCoverImage())
                    .description(entity.getDescription())
                    .duration(entity.getDuration())
                    .genres(genreAssembler.toResource(entity.getGenres()))
                    .season(entity.getSeason())
                    .seasonYear(entity.getSeasonYear())
                    .title(entity.getTitle())
                .build();

        addSelfLink(AnimeController.class, resource);

        return resource;
    }

    @Override
    public AnimeEntity toEntity(AnimeResource resource) {
        return AnimeEntity
                .builder()
                    .id(resource.getObjectId())
                    .status(resource.getStatus())
                    .bannerImage(resource.getBannerImage())
                    .coverImage(resource.getCoverImage())
                    .description(resource.getDescription())
                    .duration(resource.getDuration())
                    .genres(genreAssembler.toEntity(resource.getGenres()))
                    .season(resource.getSeason())
                    .seasonYear(resource.getSeasonYear())
                    .title(resource.getTitle())
                .build();
    }
}
