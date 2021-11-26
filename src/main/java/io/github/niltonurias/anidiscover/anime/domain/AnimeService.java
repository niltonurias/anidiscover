package io.github.niltonurias.anidiscover.anime.domain;

import io.github.niltonurias.anidiscover.enums.NotFoundEnum;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnimeService {
    private final AnimeRepository repository;

    public AnimeService(final AnimeRepository repository) {
        this.repository = repository;
    }

    public AnimeEntity create(AnimeEntity entity) {
        return this.repository.save(entity);
    }

    public AnimeEntity update(UUID id, AnimeEntity entity) {
        var oldAnime = this.findById(id);
        // TODO: merge...
        return this.repository.save(oldAnime);
    }

    public void delete(UUID id) {
        var anime = findById(id);
        this.repository.delete(anime);
    }

    public AnimeEntity findById(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException(NotFoundEnum.ANIME_NOT_FOUND));
    }

    public List<AnimeEntity> findBy() {
        //return this.repository.findBy();
        return null;
    }

    public Page<AnimeEntity> findAllPaged(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}
