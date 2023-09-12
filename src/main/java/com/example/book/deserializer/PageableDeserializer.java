package com.example.book.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;

public class PageableDeserializer extends JsonDeserializer<Pageable> {


    @Override
    public Pageable deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

        int pageNumber = node.has("page") ? node.get("page").asInt(1) : 1;
        int pageSize = node.has("size") ? node.get("size").asInt(10) : 10;

        Sort sort = Sort.unsorted();
        if (node.has("sort")) {
            String[] sortParams = node.get("sort").asText().split(",");
            if (sortParams.length > 0) {
                String property = sortParams[0];
                if (sortParams.length > 1) {
                    String direction = sortParams[1];
                    sort = Sort.by(Sort.Order.by(property).with(Sort.Direction.fromString(direction)));
                } else {
                    sort = Sort.by(Sort.Order.by(property));
                }
            }
        }

        return PageRequest.of(pageNumber - 1, pageSize, sort);
    }

}
