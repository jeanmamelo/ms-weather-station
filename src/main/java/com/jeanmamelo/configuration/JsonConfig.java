package com.jeanmamelo.configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JsonConfig {

    private final ObjectMapper mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .registerModule(new JavaTimeModule());

    @SneakyThrows
    public String objectToStringJson(Object object) {
        return mapper.writeValueAsString(object);
    }

    @SneakyThrows
    public <T> T stringJsonToObject(String json, Class<T> clazz) {
        return mapper.readValue(json, clazz);
    }
}