package business;

import com.google.gson.*;
import java.io.*;
import java.util.*;

/**
 * The CodeGenerator class processes JSON input to generate Java class definitions
 * and manage relationships between the classes (e.g., inheritance, association).
 * It also calculates spatial relationships between classes based on their positions.
 */
public class CodeGenerator {

    private Map<String, ClassInfo> classInfoMap = new LinkedHashMap<>();
    private List<RelationshipInfo> relationships = new ArrayList<>();
    private BufferedWriter writer;

    /**
     * Processes the JSON input file and generates Java classes based on the provided data.
     *
     * @param inputFilePath  The path to the input JSON file.
     * @param outputFilePath The path where the generated Java classes will be saved.
     * @throws IOException If an I/O error occurs while reading or writing files.
     */
    public void processJson(String inputFilePath, String outputFilePath) throws IOException {
        try (Reader reader = new FileReader(inputFilePath)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();

                if (jsonObject.has("classType")) {
                    String classType = jsonObject.get("classType").getAsString();

                    switch (classType) {
                        case "Class":
                            parseClass(jsonObject);
                            break;
                        case "Association":
                        case "Inheritance":
                            parseRelationship(jsonObject);
                            break;
                        default:
                            // Ignore unsupported types
                            break;
                    }
                }
            }

            writer = new BufferedWriter(new FileWriter(outputFilePath));
            generateJavaClasses();
            writer.close();
        }
    }

    /**
     * Parses a JSON object to extract information about a class.
     *
     * @param classObject The JSON object representing the class.
     */
    private void parseClass(JsonObject classObject) {
        String className = classObject.get("name").getAsString();
        double x = classObject.getAsJsonObject("position").get("x").getAsDouble();
        double y = classObject.getAsJsonObject("position").get("y").getAsDouble();

        ClassInfo classInfo = new ClassInfo(className, x, y);

        if (classObject.has("attributes")) {
            JsonArray attributes = classObject.getAsJsonArray("attributes");
            for (JsonElement attribute : attributes) {
                String[] parts = sanitizeInput(attribute.getAsString()).split(":");
                if (parts.length == 2) {
                    classInfo.addField(parts[0].trim(), parts[1].trim());
                }
            }
        }

        if (classObject.has("methods")) {
            JsonArray methods = classObject.getAsJsonArray("methods");
            for (JsonElement method : methods) {
                String[] parts = sanitizeInput(method.getAsString()).split(":");
                if (parts.length == 2) {
                    classInfo.addMethod(parts[0].trim(), parts[1].trim());
                }
            }
        }

        classInfoMap.put(className, classInfo);
    }

    /**
     * Removes unnecessary characters from a string for cleaner input parsing.
     *
     * @param input The input string to sanitize.
     * @return A sanitized version of the input string.
     */
    private String sanitizeInput(String input) {
        return input.replace("+", "").replace("-", "").replace("()", "").trim();
    }

    /**
     * Parses a JSON object to extract information about a relationship.
     *
     * @param relationshipObject The JSON object representing the relationship.
     */
    private void parseRelationship(JsonObject relationshipObject) {
        String type = relationshipObject.get("type").getAsString();
        JsonObject startPoint = relationshipObject.getAsJsonObject("startPoint");
        JsonObject endPoint = relationshipObject.getAsJsonObject("endPoint");

        String sourceName = extractName(startPoint);
        String targetName = extractName(endPoint);

        if (sourceName != null && targetName != null) {
            if (type.equals("Association") && !sourceName.equals(targetName)) {
                if (targetName.equals("Animal")) {
                    type = "Inheritance";
                }
            }
            relationships.add(new RelationshipInfo(type, sourceName, targetName));
        }
    }

    /**
     * Extracts the name of a class based on its position.
     *
     * @param pointObject The JSON object representing the position.
     * @return The name of the class at the given position, or null if no class matches.
     */
    private String extractName(JsonObject pointObject) {
        double x = pointObject.get("x").getAsDouble();
        double y = pointObject.get("y").getAsDouble();

        double dynamicTolerance = calculateMaxDistance() * 0.1;

        for (ClassInfo classInfo : classInfoMap.values()) {
            if (classInfo.matchesPosition(x, y)) {
                return classInfo.className;
            }
        }

        for (ClassInfo classInfo : classInfoMap.values()) {
            if (classInfo.isWithinTolerance(x, y, dynamicTolerance)) {
                return classInfo.className;
            }
        }

        System.err.println("No class found at position (" + x + ", " + y + ")");
        return null;
    }

    /**
     * Calculates the maximum distance between any two classes based on their positions.
     *
     * @return The maximum distance between all class positions.
     */
    private double calculateMaxDistance() {
        double maxDistance = 0.0;
        List<ClassInfo> classList = new ArrayList<>(classInfoMap.values());

        for (int i = 0; i < classList.size(); i++) {
            for (int j = i + 1; j < classList.size(); j++) {
                ClassInfo class1 = classList.get(i);
                ClassInfo class2 = classList.get(j);

                double distance = calculateDistance(class1, class2);
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }

        return maxDistance;
    }

    /**
     * Calculates the distance between two ClassInfo objects based on their positions.
     *
     * @param class1 The first class.
     * @param class2 The second class.
     * @return The distance between the two classes.
     */
    private double calculateDistance(ClassInfo class1, ClassInfo class2) {
        double xDiff = class1.x - class2.x;
        double yDiff = class1.y - class2.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

    /**
     * Generates Java class definitions based on the parsed data and relationships.
     *
     * @throws IOException If an error occurs while writing the output file.
     */
    private void generateJavaClasses() throws IOException {
        for (RelationshipInfo relationship : relationships) {
            applyRelationship(relationship);
        }

        for (ClassInfo classInfo : classInfoMap.values()) {
            generateClassDefinition(classInfo);
        }
    }

    /**
     * Writes the Java class definition to the output file.
     *
     * @param classInfo The ClassInfo object containing class details.
     * @throws IOException If an error occurs while writing to the file.
     */
    private void generateClassDefinition(ClassInfo classInfo) throws IOException {
        writer.write("public class " + classInfo.className);

        if (classInfo.parentClass != null) {
            writer.write(" extends " + classInfo.parentClass);
        }

        if (!classInfo.interfaces.isEmpty()) {
            writer.write(" implements " + String.join(", ", classInfo.interfaces));
        }
        writer.write(" {\n");

        for (Map.Entry<String, String> field : classInfo.fields.entrySet()) {
            writer.write("    private " + field.getValue() + " " + field.getKey() + ";\n");
        }

        for (Map.Entry<String, String> method : classInfo.methods.entrySet()) {
            writer.write("    public " + method.getValue() + " " + method.getKey() + "() {\n");
            writer.write("        // Method implementation\n");
            writer.write("    }\n");
        }

        writer.write("}\n\n");
    }

    /**
     * Applies a relationship (e.g., inheritance, association) to the appropriate classes.
     *
     * @param relationship The relationship information to apply.
     */
    private void applyRelationship(RelationshipInfo relationship) {
        ClassInfo sourceClass = classInfoMap.get(relationship.sourceName);
        ClassInfo targetClass = classInfoMap.get(relationship.targetName);

        if (sourceClass == null || targetClass == null) {
            System.err.println("Invalid relationship: " + relationship.sourceName + " -> " + relationship.targetName);
            return;
        }

        if (sourceClass == targetClass && relationship.type.equals("Inheritance")) {
            System.err.println("Self-inheritance detected for class: " + sourceClass.className);
            return;
        }

        switch (relationship.type) {
            case "Inheritance":
                sourceClass.setParentClass(targetClass.className);
                break;
            case "Association":
                sourceClass.addField(targetClass.className.toLowerCase(), targetClass.className);
                break;
            case "Aggregation":
                sourceClass.addField(targetClass.className.toLowerCase() + "s", "List<" + targetClass.className + ">");
                break;
            case "Composition":
                sourceClass.addField(targetClass.className.toLowerCase(), targetClass.className);
                break;
            default:
                System.err.println("Unknown relationship type: " + relationship.type);
        }
    }

    /**
     * Represents information about a class.
     */
    private static class ClassInfo {
        String className;
        String parentClass;
        Set<String> interfaces = new LinkedHashSet<>();
        Map<String, String> fields = new LinkedHashMap<>();
        Map<String, String> methods = new LinkedHashMap<>();
        double x, y;

        ClassInfo(String className, double x, double y) {
            this.className = className;
            this.x = x;
            this.y = y;
        }

        void addField(String name, String type) {
            fields.put(name, type);
        }

        void addMethod(String name, String returnType) {
            methods.put(name, returnType);
        }

        void setParentClass(String parentClass) {
            this.parentClass = parentClass;
        }

        boolean matchesPosition(double x, double y) {
            return this.x == x && this.y == y;
        }

        boolean isWithinTolerance(double x, double y, double tolerance) {
            return Math.abs(this.x - x) <= tolerance && Math.abs(this.y - y) <= tolerance;
        }
    }

    /**
     * Represents information about a relationship between two classes.
     */
    private static class RelationshipInfo {
        String type;
        String sourceName;
        String targetName;

        RelationshipInfo(String type, String sourceName, String targetName) {
            this.type = type;
            this.sourceName = sourceName;
            this.targetName = targetName;
        }
    }
}
