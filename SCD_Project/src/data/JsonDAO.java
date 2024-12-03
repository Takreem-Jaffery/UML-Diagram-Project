package data;

import business.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;

public class JsonDAO {

    public void saveAsJson(String filePath, ArrayList<Component> components) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule()).enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(filePath), components);
    }

    //deserialize
    public ArrayList<business.Component> loadFromJson(String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        //use TypeReference to handle generic types
        ArrayList<business.Component> components = objectMapper.readValue(
                new File(filePath),
                new TypeReference<ArrayList<Component>>() {}
        );
        return components;
    }


}
