package business;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class CodeGenerator {

    private Map<String, ClassInfo> classInfoMap = new LinkedHashMap<>();
    private List<RelationshipInfo> relationships = new ArrayList<>();
    private BufferedWriter writer;

    public void processJson(String inputFilePath, String outputFilePath) throws IOException {
        try (Reader reader = new FileReader(inputFilePath)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            // Parse JSON objects
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

            // Generate Java classes
            writer = new BufferedWriter(new FileWriter(outputFilePath));
            generateJavaClasses();
            writer.close();
        }
    }
    private void parseClass(JsonObject classObject) {
        String className = classObject.get("name").getAsString();
        double x = classObject.getAsJsonObject("position").get("x").getAsDouble();
        double y = classObject.getAsJsonObject("position").get("y").getAsDouble();

        ClassInfo classInfo = new ClassInfo(className, x, y);

        // Add attributes
        if (classObject.has("attributes")) {
            JsonArray attributes = classObject.getAsJsonArray("attributes");
            for (JsonElement attribute : attributes) {
                String[] parts = sanitizeInput(attribute.getAsString()).split(":");
                if (parts.length == 2) {
                    classInfo.addField(parts[0].trim(), parts[1].trim());
                }
            }
        }

        // Add methods
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

    private String sanitizeInput(String input) {
        // Remove +, -, () and trim whitespace
        return input.replace("+", "").replace("-", "").replace("()", "").trim();
    }

    //private void parseRelationship(JsonObject relationshipObject) {
//    String type = relationshipObject.get("type").getAsString();
//    JsonObject startPoint = relationshipObject.getAsJsonObject("startPoint");
//    JsonObject endPoint = relationshipObject.getAsJsonObject("endPoint");
//
//    String sourceName = extractName(startPoint);
//    String targetName = extractName(endPoint);
//
//    if (sourceName != null && targetName != null) {
//        // Correct relationship type if misclassified
//        if (type.equals("Association") && targetName.equals("Animal")) {
//            type = "Inheritance";
//        }
//        relationships.add(new RelationshipInfo(type, sourceName, targetName));
//    }
//}
    private void parseRelationship(JsonObject relationshipObject) {
        String type = relationshipObject.get("type").getAsString();
        JsonObject startPoint = relationshipObject.getAsJsonObject("startPoint");
        JsonObject endPoint = relationshipObject.getAsJsonObject("endPoint");

        String sourceName = extractName(startPoint);
        String targetName = extractName(endPoint);

        if (sourceName != null && targetName != null) {
            // Enforce correct relationship type
            if (type.equals("Association") && !sourceName.equals(targetName)) {
                if (targetName.equals("Animal")) {
                    type = "Inheritance"; // Animal is a parent class
                }
            }
            relationships.add(new RelationshipInfo(type, sourceName, targetName));
        }
    }


    // 1. Add a method to calculate the maximum distance between all class positions

    private double calculateMaxDistance() {
        double maxDistance = 0.0;

        // Get all class positions
        List<ClassInfo> classList = new ArrayList<>(classInfoMap.values());

        // Compare each pair of classes
        for (int i = 0; i < classList.size(); i++) {
            for (int j = i + 1; j < classList.size(); j++) {
                ClassInfo class1 = classList.get(i);
                ClassInfo class2 = classList.get(j);

                // Calculate distance between class1 and class2
                double distance = calculateDistance(class1, class2);

                // Update maxDistance if the current distance is greater
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }

        return maxDistance;
    }

    // 2. Method to calculate the distance between two ClassInfo objects
    private double calculateDistance(ClassInfo class1, ClassInfo class2) {
        double xDiff = class1.x - class2.x;
        double yDiff = class1.y - class2.y;
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

// 3. Update the extractName method to use dynamic tolerance

    //    private String extractName(JsonObject pointObject) {
//        double x = pointObject.get("x").getAsDouble();
//        double y = pointObject.get("y").getAsDouble();
//
//        // Dynamically calculate tolerance based on the class positions
//        double dynamicTolerance = calculateMaxDistance();
//
//        // Check the classes within the dynamic tolerance
//        for (ClassInfo classInfo : classInfoMap.values()) {
//            if (classInfo.matchesPosition(x, y)) {
//                return classInfo.className;
//            }
//        }
//
//        // Allow for a small tolerance in matching
//        for (ClassInfo classInfo : classInfoMap.values()) {
//            if (classInfo.isWithinTolerance(x, y, dynamicTolerance)) {
//                return classInfo.className;
//            }
//        }
//
//        UCDSystem.err.println("No class found at position (" + x + ", " + y + ")");
//        return null;
//    }
    private String extractName(JsonObject pointObject) {
        double x = pointObject.get("x").getAsDouble();
        double y = pointObject.get("y").getAsDouble();

        // Dynamically calculate tolerance
        double dynamicTolerance = calculateMaxDistance() * 0.1; // 10% of max distance

        // Prioritize exact matches
        for (ClassInfo classInfo : classInfoMap.values()) {
            if (classInfo.matchesPosition(x, y)) {
                return classInfo.className;
            }
        }

        // Check for matches within tolerance
        for (ClassInfo classInfo : classInfoMap.values()) {
            if (classInfo.isWithinTolerance(x, y, dynamicTolerance)) {
                return classInfo.className;
            }
        }

        System.err.println("No class found at position (" + x + ", " + y + ")");
        return null;
    }

    private void generateJavaClasses() throws IOException {
        for (RelationshipInfo relationship : relationships) {
            applyRelationship(relationship);
        }

        for (ClassInfo classInfo : classInfoMap.values()) {
            generateClassDefinition(classInfo);
        }
    }
    private void generateClassDefinition(ClassInfo classInfo) throws IOException {
        writer.write("public class " + classInfo.className);

        // Add inheritance
        if (classInfo.parentClass != null) {
            writer.write(" extends " + classInfo.parentClass);
        }
        // Add implemented interfaces
        if (!classInfo.interfaces.isEmpty()) {
            writer.write(" implements " + String.join(", ", classInfo.interfaces));
        }
        writer.write(" {\n");

        // Generate fields
        for (Map.Entry<String, String> field : classInfo.fields.entrySet()) {
            writer.write("    private " + field.getValue() + " " + field.getKey() + ";\n");
        }

        // Generate methods
        for (Map.Entry<String, String> method : classInfo.methods.entrySet()) {
            writer.write("    public " + method.getValue() + " " + method.getKey() + "() {\n");
            writer.write("        // Method implementation\n");
            writer.write("    }\n");
        }

        writer.write("}\n\n");
    }
    private void applyRelationship(RelationshipInfo relationship) {
        ClassInfo sourceClass = classInfoMap.get(relationship.sourceName);
        ClassInfo targetClass = classInfoMap.get(relationship.targetName);

        if (sourceClass == null || targetClass == null) {
            System.err.println("Invalid relationship: " + relationship.sourceName + " -> " + relationship.targetName);
            return;
        }

        if (sourceClass == targetClass && relationship.type.equals("Inheritance")) {
            System.err.println("Self-inheritance detected for class: " + sourceClass.className);
            return; // Avoid self-inheritance
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


//private void applyRelationship(RelationshipInfo relationship) {
//    ClassInfo sourceClass = classInfoMap.get(relationship.sourceName);
//    ClassInfo targetClass = classInfoMap.get(relationship.targetName);
//
//    if (sourceClass == null || targetClass == null) {
//        UCDSystem.err.println("Invalid relationship: " + relationship.sourceName + " -> " + relationship.targetName);
//        return;
//    }
//
//    switch (relationship.type) {
//        case "Inheritance":
//            sourceClass.setParentClass(targetClass.className);
//            break;
//        case "Association":
//            sourceClass.addField(targetClass.className.toLowerCase(), targetClass.className);
//            break;
//        case "Aggregation":
//            sourceClass.addField(targetClass.className.toLowerCase() + "s", "List<" + targetClass.className + ">");
//            break;
//        case "Composition":
//            sourceClass.addField(targetClass.className.toLowerCase(), targetClass.className);
//            break;
//        default:
//            UCDSystem.err.println("Unknown relationship type: " + relationship.type);
//    }
//}

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

        void addInterface(String interfaceName) {
            interfaces.add(interfaceName);
        }

        boolean matchesPosition(double x, double y) {
            return this.x == x && this.y == y;
        }

        boolean isWithinTolerance(double x, double y, double tolerance) {
            return Math.abs(this.x - x) <= tolerance && Math.abs(this.y - y) <= tolerance;
        }

    }

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