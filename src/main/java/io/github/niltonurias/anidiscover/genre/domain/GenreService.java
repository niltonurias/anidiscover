package io.github.niltonurias.anidiscover.genre.domain;

import io.github.niltonurias.anidiscover.enums.NotFoundEnum;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GenreService {
    private final GenreRepository repository;

    public GenreService(final GenreRepository repository) {
        this.repository = repository;
    }

    public GenreEntity create(GenreEntity entity) {
        return this.repository.save(entity);
    }

    public GenreEntity update(UUID id, GenreEntity entity) {
        var oldGenre = this.findById(id);
        // TODO: merge...
        return this.repository.save(oldGenre);
    }

    public void delete(UUID id) {
        var Genre = findById(id);
        this.repository.delete(Genre);
    }

    public GenreEntity findById(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException(NotFoundEnum.GENRE_NOT_FOUND));
    }

    public List<GenreEntity> findBy() {
        //return this.repository.findBy();
        return null;
    }

    public Page<GenreEntity> findAllPaged(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}
