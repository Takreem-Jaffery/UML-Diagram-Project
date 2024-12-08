package business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;
import java.util.ArrayList;

/**
 * Represents a Class in a business application. The Class can have attributes, methods,
 * a position, and a state. It implements various methods defined by the Component interface.
 *
 * <p>This class is configured to ignore unknown JSON properties during deserialization.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Class implements Component {
    /**
     * The name of the Class.
     */
    private String name;

    /**
     * The number of partitions in the Class.
     */
    private int noOfPartitions;

    /**
     * The list of attributes of the Class.
     */
    @JsonProperty("attributes")
    private ArrayList<String> attributes;

    /**
     * The list of methods of the Class.
     */
    @JsonProperty("methods")
    private ArrayList<String> methods;

    /**
     * The position of the Class represented as a {@link Point}.
     */
    private Point position;

    /**
     * The state of the Class.
     */
    private String state;

    /**
     * Constructs a Class with the specified name, number of partitions,
     * drawPartition flag, and position.
     *
     * @param name           the name of the Class
     * @param noOfPartitions the number of partitions in the Class
     * @param drawPartition  flag indicating whether to draw partitions
     * @param position       the position of the Class
     */
    public Class(String name, int noOfPartitions, boolean drawPartition, Point position) {
        this.name = name;
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.noOfPartitions = noOfPartitions;
        this.position = position;
        this.state = "";
    }

    /**
     * Constructs a default Class with the name "Class", no attributes or methods,
     * no partitions, and a default position.
     */
    public Class() {
        name = "Class";
        attributes = new ArrayList<>();
        methods = new ArrayList<>();
        noOfPartitions = 0;
        position = new Point(100, 100);
        state = "";
    }

    /**
     * Retrieves the state of the Class.
     *
     * @return the state of the Class
     */
    public String getState() {
        return state;
    }

    /**
     * Sets the state of the Class.
     *
     * @param state the new state of the Class
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Generates diagram notes for the Class. The format includes the class name,
     * partitions, attributes, and methods, if available.
     *
     * @return a string representing the diagram notes
     */
    public String getDiagramNotes() {
        String notes = "";
        notes += name + "\n";
        boolean methodWritten = false;
        for (int i = 0; i < noOfPartitions; i++) {
            notes += "--\n";
            if (i == 0 && !attributes.isEmpty()) {
                for (String attribute : attributes) {
                    notes += attribute + "\n";
                }
            } else if (i == 0 && !methods.isEmpty()) {
                for (String method : methods) {
                    notes += method + "\n";
                }
                methodWritten = true;
            } else if (i == 1 && !methods.isEmpty() && methodWritten) {
                for (String method : methods) {
                    notes += method + "\n";
                }
            }
        }
        return notes;
    }

    /**
     * Adds an attribute to the Class.
     *
     * @param att the attribute to add
     */
    public void addAttribute(String att) {
        attributes.add(att);
    }

    /**
     * Adds a method to the Class.
     *
     * @param m the method to add
     */
    public void addMethod(String m) {
        methods.add(m);
    }

    /**
     * Retrieves the name of the Class.
     *
     * @return the name of the Class
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Class.
     *
     * @param name the new name of the Class
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the position of the Class.
     *
     * @return the position of the Class
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets the position of the Class.
     *
     * @param position the new position of the Class
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Retrieves the number of partitions in the Class.
     *
     * @return the number of partitions in the Class
     */
    public int getNoOfPartitions() {
        return noOfPartitions;
    }

    /**
     * Sets the number of partitions in the Class.
     *
     * @param noOfPartitions the new number of partitions
     */
    public void setNoOfPartitions(int noOfPartitions) {
        this.noOfPartitions = noOfPartitions;
    }

    /**
     * Retrieves the list of attributes in the Class.
     *
     * @return the list of attributes
     */
    public ArrayList<String> getAttributes() {
        return attributes;
    }

    /**
     * Sets the attributes of the Class.
     *
     * @param attributes the new list of attributes
     */
    public void setAttributes(ArrayList<String> attributes) {
        this.attributes = attributes;
    }

    /**
     * Retrieves the list of methods in the Class.
     *
     * @return the list of methods
     */
    public ArrayList<String> getMethods() {
        return methods;
    }

    /**
     * Sets the methods of the Class.
     *
     * @param methods the new list of methods
     */
    public void setMethods(ArrayList<String> methods) {
        this.methods = methods;
    }

    /**
     * Displays the Class. (Currently not implemented.)
     */
    @Override
    public void display() {
        // Implementation to be added
    }

    /**
     * Adds a property to the Class. (Currently not implemented.)
     */
    @Override
    public void addProperty() {
        // Implementation to be added
    }

    /**
     * Removes a property from the Class. (Currently not implemented.)
     */
    @Override
    public void removeProperty() {
        // Implementation to be added
    }

    /**
     * Adds a constraint to the Class. (Currently not implemented.)
     */
    @Override
    public void addConstraint() {
        // Implementation to be added
    }

    /**
     * Removes a constraint from the Class. (Currently not implemented.)
     */
    @Override
    public void removeConstraint() {
        // Implementation to be added
    }

    /**
     * Retrieves the class type of the object, which is "Class".
     *
     * @return the class type as a string
     */
    @Override
    public String getClassType() {
        return "Class";
    }
}
