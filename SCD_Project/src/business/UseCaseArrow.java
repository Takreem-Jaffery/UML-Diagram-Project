package business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.*;

/**
 * Represents an arrow in a UML use case diagram. This class implements the {@link Component} interface
 * and provides properties such as name, type, start point, and end point of the arrow.
 *
 * <p>The {@code UseCaseArrow} class is annotated with {@code @JsonIgnoreProperties(ignoreUnknown = true)},
 * making it compatible with JSON serialization/deserialization using the Jackson library.</p>
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UseCaseArrow implements Component {

    /** The name of the arrow. */
    private String name;

    /** The type of the arrow (e.g., dependency, association, etc.). */
    private String type;

    /** The starting point of the arrow in 2D space. */
    private Point startPoint;

    /** The ending point of the arrow in 2D space. */
    private Point endPoint;

    /**
     * Constructs a {@code UseCaseArrow} with the specified name, type, start point, and end point.
     *
     * @param name       the name of the arrow
     * @param type       the type of the arrow
     * @param startPoint the starting point of the arrow
     * @param endPoint   the ending point of the arrow
     */
    public UseCaseArrow(String name, String type, Point startPoint, Point endPoint) {
        this.name = name;
        this.type = type;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    /**
     * Constructs a {@code UseCaseArrow} with default values.
     * <p>The default name and type are empty strings, and the start and end points are {@code null}.</p>
     */
    public UseCaseArrow() {
        name = "";
        type = "";
        startPoint = null;
        endPoint = null;
    }

    /**
     * Displays the arrow.
     * <p>Note: Implementation to be added.</p>
     */
    @Override
    public void display() {
        // Implementation to be added
    }

    /**
     * Adds a property to the arrow.
     * <p>Note: Implementation to be added.</p>
     */
    @Override
    public void addProperty() {
        // Implementation to be added
    }

    /**
     * Removes a property from the arrow.
     * <p>Note: Implementation to be added.</p>
     */
    @Override
    public void removeProperty() {
        // Implementation to be added
    }

    /**
     * Adds a constraint to the arrow.
     * <p>Note: Implementation to be added.</p>
     */
    @Override
    public void addConstraint() {
        // Implementation to be added
    }

    /**
     * Removes a constraint from the arrow.
     * <p>Note: Implementation to be added.</p>
     */
    @Override
    public void removeConstraint() {
        // Implementation to be added
    }

    /**
     * Returns the type of this component as a string.
     *
     * @return "Arrow" as the class type
     */
    @Override
    public String getClassType() {
        return "Arrow";
    }

    /**
     * Returns the type of the arrow.
     *
     * @return the type of the arrow
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the arrow.
     *
     * @param type the new type of the arrow
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the name of the arrow.
     *
     * @return the name of the arrow
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the arrow.
     *
     * @param name the new name of the arrow
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the ending point of the arrow.
     *
     * @return the ending point of the arrow
     */
    public Point getEndPoint() {
        return endPoint;
    }

    /**
     * Sets the ending point of the arrow.
     *
     * @param endPoint the new ending point of the arrow
     */
    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * Returns the starting point of the arrow.
     *
     * @return the starting point of the arrow
     */
    public Point getStartPoint() {
        return startPoint;
    }

    /**
     * Sets the starting point of the arrow.
     *
     * @param startPoint the new starting point of the arrow
     */
    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }
}
