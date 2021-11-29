package io.github.niltonurias.anidiscover.anime.endpoint;

import io.github.niltonurias.anidiscover.anime.domain.AnimeEntity;
import io.github.niltonurias.anidiscover.anime.domain.AnimeService;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.ConflictException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/anime")
@CrossOrigin
public record AnimeController(AnimeAssembler assembler, AnimeService service) {

    @PostMapping
    public ResponseEntity<AnimeResource> create(@RequestBody AnimeResource anime) {
        try {
            var animePersisted = this.service.create(this.assembler.toEntity(anime));
            return ResponseEntity.status(HttpStatus.CREATED).body(this.assembler.toResource(animePersisted));
        } catch (ConflictException exception) {
            if (exception.getObject() instanceof AnimeEntity entity) {
                throw new ConflictException(this.assembler.toResource(entity));
            }

            throw exception;
        }
    }

    @PatchMapping("/{id}")
    public AnimeResource update(@PathVariable UUID id, @RequestBody AnimeResource anime) {
        var animeUpdated = this.service.update(id, this.assembler.toEntity(anime));
        return this.assembler.toResource(animeUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public AnimeResource findById(@PathVariable UUID id) {
        return this.assembler.toResource(this.service.findById(id));
    }

    @GetMapping
    public PagedModel<AnimeResource> findAll(@SortDefault("title") Pageable pageable) {
        var entityPage = this.service.findAllPaged(pageable);
        return this.assembler.toPaged(entityPage);
    }
}
