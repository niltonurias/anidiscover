package io.github.niltonurias.anidiscover.genre.endpoint;

import io.github.niltonurias.anidiscover.genre.domain.GenreService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/genre")
@CrossOrigin
public class GenreController {
    private final GenreAssembler assembler;
    private final GenreService service;

    public GenreController(final GenreAssembler assembler, final GenreService service) {
        this.assembler = assembler;
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<GenreResource> create(@RequestBody GenreResource anime) {
        var animePersisted = this.service.create(this.assembler.toEntity(anime));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.assembler.toResource(animePersisted));
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
    public Page<GenreResource> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                       @RequestParam(required = false, defaultValue = "20") int size) {
        var entityPage = this.service.findAllPaged(PageRequest.of(page, size));
        var resources = entityPage.getContent().stream().map(assembler::toResource).toList();
        return new PageImpl<>(resources, entityPage.getPageable(), entityPage.getTotalElements());
    }
}
