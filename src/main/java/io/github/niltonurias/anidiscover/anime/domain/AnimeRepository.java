package io.github.niltonurias.anidiscover.anime.domain;

import io.github.niltonurias.anidiscover.enums.SeasonEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AnimeRepository extends JpaRepository<AnimeEntity, UUID> {
    Optional<AnimeEntity> findByTitleAndSeasonAndSeasonYear(String title, SeasonEnum season, Long seasonYear);
}
