package io.github.niltonurias.anidiscover.anime;

import io.github.niltonurias.anidiscover.anime.endpoint.AnimeResource;
import io.github.niltonurias.anidiscover.enums.SeasonEnum;
import io.github.niltonurias.anidiscover.enums.StatusEnum;
import io.github.niltonurias.anidiscover.genre.endpoint.GenreResource;
import io.github.niltonurias.anidiscover.infrastructure.AbstractTester;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AnimeControllerTest extends AbstractTester {
    private static List<GenreResource> genres;

    @BeforeEach
    public void setup() throws Exception {
        genres = new ArrayList<>();

        genres.add(createAndReturn("/genre", GenreResource.class, GenreResource.builder().name("Action").build(), MediaType.APPLICATION_JSON));
        genres.add(createAndReturn("/genre", GenreResource.class, GenreResource.builder().name("Adventure").build(), MediaType.APPLICATION_JSON));
        genres.add(createAndReturn("/genre", GenreResource.class, GenreResource.builder().name("Comedy").build(), MediaType.APPLICATION_JSON));
    }

    @Test
    public void shouldCreateAnAnime() throws Exception {
        var created = createAnime(0);
        performPost("/anime", created)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title", is(created.getTitle())));
    }

    @Test
    public void shouldReturnAConflictedAnime() throws Exception {
        var created = createAnime(7);
        performPost("/anime", created)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(created.getTitle())));

        var conflicted = createAnime(7);
        performPost("/anime", conflicted)
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.title", is(conflicted.getTitle())));
    }

    @Test
    public void shouldUpdateAnAnime() throws Exception {
        var persistedAnime = createAndReturn("/anime", AnimeResource.class, createAnime(1), MediaType.APPLICATION_JSON);

        var updateAction = createAnime(2);

        performPatch("/anime/{id}", updateAction, persistedAnime.getObjectId().toString())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is(updateAction.getTitle())));
    }

    @Test
    public void shouldDeleteAnAnime() throws Exception {
        var persistedAnime = createAndReturn("/anime", AnimeResource.class, createAnime(3), MediaType.APPLICATION_JSON);

        performDelete("/anime/{id}", persistedAnime.getObjectId().toString())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetAllGenre() throws Exception {
        var persistedAnime1 = createAndReturn("/anime", AnimeResource.class, createAnime(4), MediaType.APPLICATION_JSON);
        var persistedAnime2 = createAndReturn("/anime", AnimeResource.class, createAnime(5), MediaType.APPLICATION_JSON);

        performGet("/anime")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalElements", greaterThan(0)));
    }

    @Test
    public void shouldGetAnimeById() throws Exception {
        var persistedAnime = createAndReturn("/anime", AnimeResource.class, createAnime(6), MediaType.APPLICATION_JSON);

        performGet("/anime/{id}", persistedAnime.getObjectId().toString())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(persistedAnime.getObjectId().toString())))
                .andExpect(jsonPath("$.title", is(persistedAnime.getTitle())));
    }

    private AnimeResource createAnime(int which) {
        String[] titles = {
                "One Piece",
                "Takt op. Destiny",
                "Sekai Saikou no Ansatsusha, Isekai Kizoku ni Tensei suru",
                "Shinka no Mi: Shiranai Uchi ni Kachigumi Jinsei",
                "Shin no Nakama ja Nai to Yuusha no Party wo Oidasareta node, Henkyou de Slow Life suru Koto ni shimashita",
                "Xian Wang De Richang Shenghuo 2",
                "Shigatsu wa Kimi no Uso",
                "Boku no Hero Academia"
        };

        return AnimeResource
                .builder()
                    .bannerImage("https://images.com/banner.png")
                    .coverImage("https://images.com/cover.png")
                    .description("A just simple description about an anime!")
                    .duration(24L)
                    .genres(genres)
                    .status(StatusEnum.RELEASING)
                    .season(SeasonEnum.FALL)
                    .seasonYear(2021L)
                    .title(titles[which])
                .build();
    }
}
