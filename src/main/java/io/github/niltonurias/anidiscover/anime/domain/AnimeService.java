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

        if (!Objects.isNull(existEntity)) {
            throw new ConflictException(existEntity);
        }

        return this.repository.save(entity);
    }

    public AnimeEntity update(UUID id, AnimeEntity entity) {
        var oldAnime = this.findById(id);
        merge(oldAnime, entity);
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

    private void merge(AnimeEntity oldEntity, AnimeEntity newEntity) {
        if (Strings.isNotBlank(newEntity.getBannerImage()))
            oldEntity.setBannerImage(newEntity.getBannerImage());

        if (Strings.isNotBlank(newEntity.getCoverImage()))
            oldEntity.setCoverImage(newEntity.getCoverImage());

        if (Strings.isNotBlank(newEntity.getDescription()))
            oldEntity.setDescription(newEntity.getDescription());

        if (!Objects.isNull(newEntity.getDuration()))
            oldEntity.setDuration(newEntity.getDuration());

        if (ObjectUtils.isEmpty(newEntity.getGenres()))
            oldEntity.setGenres(newEntity.getGenres());

        if (!Objects.isNull(newEntity.getSeason()))
            oldEntity.setSeason(newEntity.getSeason());

        if (!Objects.isNull(newEntity.getSeasonYear()))
            oldEntity.setSeasonYear(newEntity.getSeasonYear());

        if (!Objects.isNull(newEntity.getStatus()))
            oldEntity.setStatus(newEntity.getStatus());

        if (Strings.isNotBlank(newEntity.getTitle()))
            oldEntity.setTitle(newEntity.getTitle());
    }
}
