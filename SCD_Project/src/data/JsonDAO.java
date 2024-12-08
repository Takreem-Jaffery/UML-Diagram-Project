package data;

import business.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A Data Access Object (DAO) class for handling JSON serialization and deserialization
 * of {@link Component} objects. Uses the Jackson library to save and load components
 * to and from JSON files.
 */
public class JsonDAO {

    /**
     * Saves a list of {@link Component} objects as a formatted JSON file.
     *
     * @param filePath   the file path where the JSON file will be saved
     * @param components the list of {@code Component} objects to serialize
     * @throws IOException if an error occurs during the writing process
     */
    public void saveAsJson(String filePath, ArrayList<Component> components) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filePath), components);
    }

    /**
     * Loads a list of {@link Component} objects from a JSON file.
     *
     * @param filePath the file path of the JSON file to read
     * @return an {@code ArrayList} of {@code Component} objects deserialized from the JSON file
     * @throws IOException if an error occurs during the reading process
     */
    public ArrayList<Component> loadFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Use TypeReference to handle the generic type ArrayList<Component>
        ArrayList<Component> components = objectMapper.readValue(
                new File(filePath),
                new TypeReference<ArrayList<Component>>() {}
        );
        return components;
    }
}
