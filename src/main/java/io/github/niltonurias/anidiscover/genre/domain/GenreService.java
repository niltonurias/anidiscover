package io.github.niltonurias.anidiscover.genre.domain;

import io.github.niltonurias.anidiscover.enums.ExceptionEnum;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.ConflictException;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.NotFoundException;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public record GenreService(GenreRepository repository) {

    public GenreEntity create(GenreEntity entity) {
        var existGenre = this.repository.findByName(entity.getName());
        existGenre.ifPresent(genre -> { throw new ConflictException(genre); });
        return this.repository.save(entity);
    }

    public GenreEntity update(UUID id, GenreEntity entity) {
        var oldGenre = this.findById(id);
        oldGenre.mergeWith(entity);
        return this.repository.save(oldGenre);
    }

    public void delete(UUID id) {
        var Genre = findById(id);
        this.repository.delete(Genre);
    }

    public GenreEntity findById(UUID id) {
        return this.repository.findById(id).orElseThrow(() -> new NotFoundException(ExceptionEnum.GENRE_NOT_FOUND));
    }

    public List<GenreEntity> findBy() {
        throw new NotYetImplementedException();
    }

    public Page<GenreEntity> findAllPaged(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}
