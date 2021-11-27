package io.github.niltonurias.anidiscover.genre.endpoint;

import io.github.niltonurias.anidiscover.genre.domain.GenreEntity;
import io.github.niltonurias.anidiscover.genre.domain.GenreService;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.ConflictException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/genre")
@CrossOrigin
public record GenreController(GenreAssembler assembler, GenreService service) {

    @PostMapping
    public ResponseEntity<GenreResource> create(@RequestBody GenreResource anime) {
        try {
            var animePersisted = this.service.create(this.assembler.toEntity(anime));
            return ResponseEntity.status(HttpStatus.CREATED).body(this.assembler.toResource(animePersisted));
        } catch (ConflictException exception) {
            if (exception.getObject() instanceof GenreEntity entity) {
                throw new ConflictException(this.assembler.toResource(entity));
            }

            throw exception;
        }
    }

    @PatchMapping("/{id}")
    public GenreResource update(@PathVariable UUID id, @RequestBody GenreResource genre) {
        var animeUpdated = this.service.update(id, this.assembler.toEntity(genre));
        return this.assembler.toResource(animeUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public GenreResource findById(@PathVariable UUID id) {
        return this.assembler.toResource(this.service.findById(id));
    }

    @GetMapping
    public Page<GenreResource> findAll(Pageable pageable) {
        var entityPage = this.service.findAllPaged(pageable);
        var resources = entityPage.getContent().stream().map(assembler::toResource).toList();
        return new PageImpl<>(resources, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
