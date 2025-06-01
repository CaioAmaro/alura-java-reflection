package br.com.alura.refl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.ObjectStreamException;
import java.util.Arrays;
import java.util.HashMap;

public class ObjectToJson {
    public String transform(Object object){
        String result = null;

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        HashMap<String, Object> mapper = new HashMap<>();

        Class<?> classToBeTransforme = object.getClass();

        Arrays.stream(classToBeTransforme.getDeclaredFields()).toList().forEach(
                field -> {
                    field.setAccessible(true);
                    String key = field.getName();
                    Object value = null;
                    try {
                        value = field.get(object);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    mapper.put(key,value);
                }
        );

        try {
            result = objectMapper.writeValueAsString(mapper);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }
}
