package business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.*;

/**
 * Represents an Actor in a business application. An Actor has a name and a position,
 * and implements various methods defined by the Component interface.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Actor implements Component {
    /**
     * The name of the Actor.
     */
    String name;

    /**
     * The position of the Actor represented as a {@link Point}.
     */
    Point position;

    /**
     * Constructs an Actor with a specified name and position.
     *
     * @param name     the name of the Actor
     * @param position the position of the Actor
     */
    public Actor(String name, Point position) {
        this.name = name;
        this.position = position;
    }

    /**
     * Constructs a default Actor with the name "Actor" and no position.
     */
    public Actor() {
        name = "Actor";
        position = null;
    }

    /**
     * Retrieves the name of the Actor.
     *
     * @return the name of the Actor
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Actor.
     *
     * @param name the new name of the Actor
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the position of the Actor.
     *
     * @return the position of the Actor
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets the position of the Actor.
     *
     * @param position the new position of the Actor
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Displays the Actor. (Currently not implemented.)
     */
    @Override
    public void display() {
        // Implementation here
    }

    /**
     * Adds a property to the Actor. (Currently not implemented.)
     */
    @Override
    public void addProperty() {
        // Implementation here
    }

    /**
     * Removes a property from the Actor. (Currently not implemented.)
     */
    @Override
    public void removeProperty() {
        // Implementation here
    }

    /**
     * Adds a constraint to the Actor. (Currently not implemented.)
     */
    @Override
    public void addConstraint() {
        // Implementation here
    }

    /**
     * Removes a constraint from the Actor. (Currently not implemented.)
     */
    @Override
    public void removeConstraint() {
        // Implementation here
    }

    /**
     * Retrieves the class type of the Actor, which is "Actor".
     *
     * @return the class type of the Actor
     */
    @Override
    public String getClassType() {
        return "Actor";
    }
}
