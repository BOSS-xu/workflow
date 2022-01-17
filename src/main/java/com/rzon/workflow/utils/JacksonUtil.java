package com.rzon.workflow.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzon.workflow.exception.InternalServerException;

import java.io.IOException;
import java.util.Objects;

public final class JacksonUtil {
    
    private static ObjectMapper objectMapper = new ObjectMapper();

    private JacksonUtil() { }

    public static String serialize(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new InternalServerException("json serialize error", e);
        }
    }

    public static <T> T deserialize(final String value, final TypeReference<T> valueTypeRef) {
        Objects.requireNonNull(value);
        try {
            return objectMapper.readValue(value, valueTypeRef);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerException("json deserialize error", e);
        }
    }
    
    public static <T> T deserialize(final String value, final Class<T> valueType) {
        Objects.requireNonNull(value);
        try {
            return objectMapper.readValue(value, valueType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerException("json deserialize error", e);
        }
    }
}
