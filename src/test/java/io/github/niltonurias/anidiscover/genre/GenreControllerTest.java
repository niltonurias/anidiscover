package io.github.niltonurias.anidiscover.genre;

import io.github.niltonurias.anidiscover.genre.endpoint.GenreResource;
import io.github.niltonurias.anidiscover.infrastructure.AbstractTester;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

public class GenreControllerTest extends AbstractTester {
    @Test
    public void shouldCreateAGenre() throws Exception {
        var created = GenreResource.builder().name("Created").build();
        performPost("/genre", created)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(created.getName())));
    }

    @Test
    public void shouldUpdateAGenre() throws Exception {
        var action = GenreResource.builder().name("Action").build();
        var persistedAction = createAndReturn("/genre", GenreResource.class, action, MediaType.APPLICATION_JSON);

        var updateAction = GenreResource.builder().name("Action 2").build();

        performPatch("/genre/{id}", updateAction, persistedAction.getObjectId().toString())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(updateAction.getName())));
    }

    @Test
    public void shouldDeleteAGenre() throws Exception {
        var action = GenreResource.builder().name("Action").build();
        var persistedAction = createAndReturn("/genre", GenreResource.class, action, MediaType.APPLICATION_JSON);

        performDelete("/genre/{id}", persistedAction.getObjectId().toString())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetAllGenre() throws Exception {
        var action = GenreResource.builder().name("Action").build();
        var adventure = GenreResource.builder().name("Adventure").build();
        var persistedAction = createAndReturn("/genre", GenreResource.class, action, MediaType.APPLICATION_JSON);
        var persistedAdventure = createAndReturn("/genre", GenreResource.class, adventure, MediaType.APPLICATION_JSON);

        performGet("/genre")
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.page.totalElements", greaterThan(0)))
            .andExpect(jsonPath("$._embedded.genres.[0].id", is(persistedAction.getObjectId().toString())))
            .andExpect(jsonPath("$._embedded.genres.[0].name", is(persistedAction.getName())))
            .andExpect(jsonPath("$._embedded.genres.[1].id", is(persistedAdventure.getObjectId().toString())))
            .andExpect(jsonPath("$._embedded.genres.[1].name", is(persistedAdventure.getName())));
    }

    @Test
    public void shouldGetGenreById() throws Exception {
        var action = GenreResource.builder().name("Action").build();
        var persistedAction = createAndReturn("/genre", GenreResource.class, action, MediaType.APPLICATION_JSON);

        performGet("/genre/{id}", persistedAction.getObjectId().toString())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(persistedAction.getObjectId().toString())))
                .andExpect(jsonPath("$.name", is(persistedAction.getName())));
    }
}
