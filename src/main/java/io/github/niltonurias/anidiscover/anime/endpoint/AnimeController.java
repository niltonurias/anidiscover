package io.github.niltonurias.anidiscover.anime.endpoint;

import io.github.niltonurias.anidiscover.anime.domain.AnimeEntity;
import io.github.niltonurias.anidiscover.anime.domain.AnimeService;
import io.github.niltonurias.anidiscover.infrastructure.exceptions.ConflictException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/anime")
@CrossOrigin
public class AnimeController {

    private final AnimeAssembler assembler;
    private final AnimeService service;

    public AnimeController(final AnimeAssembler assembler, final AnimeService service) {
        this.assembler = assembler;
        this.service = service;
    }

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
    public Page<AnimeResource> findAll(@SortDefault("title") Pageable pageable) {
        var entityPage = this.service.findAllPaged(pageable);
        var resources = entityPage.getContent().stream().map(assembler::toResource).toList();
        return new PageImpl<>(resources, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
