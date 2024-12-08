package business;

import java.awt.Point;

/**
 * Represents an Association in a business application. An Association connects
 * two points, has a name, and a type, and implements various methods defined
 * by the Component interface.
 */
public class Association implements Component {
    /**
     * The type of the Association.
     */
    String type;

    /**
     * The name of the Association.
     */
    String name;

    /**
     * The starting point of the Association.
     */
    Point startPoint;

    /**
     * The ending point of the Association.
     */
    Point endPoint;

    /**
     * Constructs a default Association with empty type and name,
     * and null start and end points.
     */
    public Association() {
        type = "";
        name = "";
        startPoint = null;
        endPoint = null;
    }

    /**
     * Constructs an Association with the specified type, name,
     * start point, and end point.
     *
     * @param type       the type of the Association
     * @param name       the name of the Association
     * @param startPoint the starting point of the Association
     * @param endPoint   the ending point of the Association
     */
    public Association(String type, String name, Point startPoint, Point endPoint) {
        this.type = type;
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    /**
     * Retrieves the type of the Association.
     *
     * @return the type of the Association
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type of the Association.
     *
     * @param type the new type of the Association
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retrieves the name of the Association.
     *
     * @return the name of the Association
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Association.
     *
     * @param name the new name of the Association
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the starting point of the Association.
     *
     * @return the starting point of the Association
     */
    public Point getStartPoint() {
        return startPoint;
    }

    /**
     * Sets the starting point of the Association.
     *
     * @param startPoint the new starting point of the Association
     */
    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * Retrieves the ending point of the Association.
     *
     * @return the ending point of the Association
     */
    public Point getEndPoint() {
        return endPoint;
    }

    /**
     * Sets the ending point of the Association.
     *
     * @param endPoint the new ending point of the Association
     */
    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * Displays the Association. (Currently not implemented.)
     */
    @Override
    public void display() {
        // Implementation here
    }

    /**
     * Adds a property to the Association. (Currently not implemented.)
     */
    @Override
    public void addProperty() {
        // Implementation here
    }

    /**
     * Removes a property from the Association. (Currently not implemented.)
     */
    @Override
    public void removeProperty() {
        // Implementation here
    }

    /**
     * Adds a constraint to the Association. (Currently not implemented.)
     */
    @Override
    public void addConstraint() {
        // Implementation here
    }

    /**
     * Removes a constraint from the Association. (Currently not implemented.)
     */
    @Override
    public void removeConstraint() {
        // Implementation here
    }

    /**
     * Retrieves the class type of the Association, which is "Association".
     *
     * @return the class type of the Association
     */
    @Override
    public String getClassType() {
        return "Association";
    }
}
