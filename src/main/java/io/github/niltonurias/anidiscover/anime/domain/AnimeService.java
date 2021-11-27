package io.github.niltonurias.anidiscover.anime.domain;

import io.github.niltonurias.anidiscover.enums.ExceptionEnum;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.ConflictException;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.NotFoundException;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public record AnimeService(AnimeRepository repository) {

    public AnimeEntity create(AnimeEntity entity) {
        var existEntity = this.repository.findByTitleAndSeasonAndSeasonYear(entity.getTitle(), entity.getSeason(), entity.getSeasonYear());
        existEntity.ifPresent(anime -> { throw new ConflictException(anime); });
        return this.repository.save(entity);
    }

    public AnimeEntity update(UUID id, AnimeEntity entity) {
        var oldAnime = this.findById(id);
        oldAnime.mergeWith(entity);
        return this.repository.save(oldAnime);
    }

    public void delete(UUID id) {
        var anime = findById(id);
        this.repository.delete(anime);
    }

    public AnimeEntity findById(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException(ExceptionEnum.ANIME_NOT_FOUND));
    }

    public List<AnimeEntity> findBy() {
        throw new NotYetImplementedException();
    }

    public Page<AnimeEntity> findAllPaged(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}
