package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JsonReaderUtil {

    private static final ObjectMapper objectMapper =
            new ObjectMapper();

    public static JsonNode readJsonFile(String filePath) {

        try {

            return objectMapper.readTree(
                    new File(filePath)
            );

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to read JSON file: " + filePath,
                    e
            );
        }
    }
}