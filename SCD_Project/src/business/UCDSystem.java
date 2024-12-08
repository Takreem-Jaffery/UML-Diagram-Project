/**
 * Represents a system component in a Use Case Diagram (UCD) with
 * a name, position, dimensions, and associated properties and constraints.
 */
package business;

import java.awt.Point;

public class UCDSystem implements Component {
    /**
     * Name of the system.
     */
    String name;

    /**
     * Position of the system on the diagram, represented as a point.
     */
    Point position;

    /**
     * Width of the system representation in the diagram.
     */
    int width;

    /**
     * Height of the system representation in the diagram.
     */
    int height;

    /**
     * Constructs a UCDSystem with specified name, position, width, and height.
     *
     * @param name the name of the system
     * @param p the position of the system
     * @param w the width of the system
     * @param h the height of the system
     */
    public UCDSystem(String name, Point p, int w, int h) {
        this.name = name;
        this.position = p;
        this.width = w;
        this.height = h;
    }

    /**
     * Constructs a UCDSystem with default values.
     * Default name is "System", position is null,
     * width is 150, and height is 100.
     */
    public UCDSystem() {
        name = "System";
        position = null;
        width = 150;
        height = 100;
    }

    /**
     * Gets the width of the system.
     *
     * @return the width of the system
     */
    public int getWidth() {
        return width;
    }

    /**
     * Sets the width of the system.
     *
     * @param width the new width of the system
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Gets the height of the system.
     *
     * @return the height of the system
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the system.
     *
     * @param height the new height of the system
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the name of the system.
     *
     * @return the name of the system
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the system.
     *
     * @param name the new name of the system
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the position of the system.
     *
     * @return the position of the system
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets the position of the system.
     *
     * @param position the new position of the system
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Displays the system. This method is currently unimplemented.
     */
    @Override
    public void display() {
        // Implementation pending
    }

    /**
     * Adds a property to the system. This method is currently unimplemented.
     */
    @Override
    public void addProperty() {
        // Implementation pending
    }

    /**
     * Removes a property from the system. This method is currently unimplemented.
     */
    @Override
    public void removeProperty() {
        // Implementation pending
    }

    /**
     * Adds a constraint to the system. This method is currently unimplemented.
     */
    @Override
    public void addConstraint() {
        // Implementation pending
    }

    /**
     * Removes a constraint from the system. This method is currently unimplemented.
     */
    @Override
    public void removeConstraint() {
        // Implementation pending
    }

    /**
     * Gets the type of the class as a string.
     *
     * @return the class type as "System"
     */
    @Override
    public String getClassType() {
        return "System";
    }
}
