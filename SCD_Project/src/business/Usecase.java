/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.*;

/**
 * Represents a Use Case in a UML diagram. This class implements the {@link Component} interface
 * and provides properties such as the name and position of the use case.
 * It also includes methods for displaying the use case and managing its properties and constraints.
 *
 * <p>The {@code Usecase} class is annotated with {@code @JsonIgnoreProperties(ignoreUnknown = true)},
 * making it compatible with JSON serialization/deserialization using the Jackson library.</p>
 *
 * @author hp
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usecase implements Component {

    /** The name of the use case. */
    private String name;

    /** The position of the use case in a 2D space. */
    private Point position;

    /**
     * Constructs a {@code Usecase} with the specified name and position.
     *
     * @param name the name of the use case
     * @param position the position of the use case
     */
    public Usecase(String name, Point position) {
        this.name = name;
        this.position = position;
    }

    /**
     * Constructs a {@code Usecase} with default values.
     * The default name is "Usecase" and the position is {@code null}.
     */
    public Usecase() {
        name = "Usecase";
        position = null;
    }

    /**
     * Returns the name of the use case.
     *
     * @return the name of the use case
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the use case.
     *
     * @param name the new name of the use case
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the position of the use case.
     *
     * @return the position of the use case
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets the position of the use case.
     *
     * @param position the new position of the use case
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Returns a string representing the diagram notes of the use case.
     *
     * @return a string containing the name of the use case followed by a newline
     */
    public String getDiagramNotes() {
        return name + "\n";
    }

    /**
     * Displays the use case.
     * <p>Note: Implementation to be added.</p>
     */
    @Override
    public void display() {
        // Implementation to be added
    }

    /**
     * Adds a property to the use case.
     * <p>Note: Implementation to be added.</p>
     */
    @Override
    public void addProperty() {
        // Implementation to be added
    }

    /**
     * Removes a property from the use case.
     * <p>Note: Implementation to be added.</p>
     */
    @Override
    public void removeProperty() {
        // Implementation to be added
    }

    /**
     * Adds a constraint to the use case.
     * <p>Note: Implementation to be added.</p>
     */
    @Override
    public void addConstraint() {
        // Implementation to be added
    }

    /**
     * Removes a constraint from the use case.
     * <p>Note: Implementation to be added.</p>
     */
    @Override
    public void removeConstraint() {
        // Implementation to be added
    }

    /**
     * Returns the type of this component as a string.
     *
     * @return "UseCase" as the class type
     */
    @Override
    public String getClassType() {
        return "UseCase";
    }
}
