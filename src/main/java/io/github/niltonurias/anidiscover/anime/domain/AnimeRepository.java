package io.github.niltonurias.anidiscover.anime.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AnimeRepository extends JpaRepository<AnimeEntity, UUID> {
}
