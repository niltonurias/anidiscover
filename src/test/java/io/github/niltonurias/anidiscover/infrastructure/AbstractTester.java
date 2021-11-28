package io.github.niltonurias.anidiscover.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AbstractTester {
    @Autowired
    public MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    public ResultActions performPost(String uri, Object content) throws Exception {
        return mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(content)));
    }

    public ResultActions performPatch(String uri, Object content, String... args) throws Exception {
        return mockMvc.perform(patch(uri, args).contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(content)));
    }

    public ResultActions performDelete(String uri, String... args) throws Exception {
        return mockMvc.perform(delete(uri, args).contentType(MediaType.APPLICATION_JSON));
    }

    public ResultActions performGet(String uri, String... args) throws Exception {
        return mockMvc.perform(get(uri, args).contentType(MediaType.APPLICATION_JSON));
    }

    public <T> T createAndReturn(String uri, Class<T> klass, T content, MediaType mediaType) throws Exception {
        return mapper.readValue(
                mockMvc.perform(post(uri)
                        .contentType(mediaType)
                        .content(mapper.writeValueAsString(content)))
                    .andReturn()
                    .getResponse()
                    .getContentAsString(), klass);
    }
}
