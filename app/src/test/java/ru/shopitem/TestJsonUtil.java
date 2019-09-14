package ru.shopitem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class TestJsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readFromJsonMvcResult(MvcResult result, Class<T> clazz) throws IOException {
        return objectMapper.readValue(getContent(result), clazz);
    }

    public static <T> List<T> readListFromJsonMvcResult(MvcResult result, TypeReference<T> reference) throws IOException {
        return objectMapper.readValue(getContent(result), reference);
    }

    private static String getContent(MvcResult result) throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString();
    }

    public static <T> T readFromJsonResultAction(ResultActions action, Class<T> clazz) throws IOException {
        return objectMapper.readValue(getContent(action.andReturn()), clazz);
    }

    public static <T> String writeValueToJson(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
