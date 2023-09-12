package com.example.book.deserializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;

public class DeserializerObjectMapper {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Pageable.class, new PageableDeserializer());
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
