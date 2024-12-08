package business;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;

/**
 * Represents a comment component used in a graphical application context. This class implements the
 * {@link Component} interface and provides properties and methods related to a comment's state,
 * position, and textual content. The class is likely utilized in a diagramming, modeling, or similar UI system
 * for visualizing comment annotations within a graphical editor or application.
 */
public class Comment implements Component {

    /** The name of the comment. */
    String name;

    /** The text content of the comment. */
    String commentText;

    /** The position of the comment in a 2D graphical space. */
    Point position;

    /**
     * Constructor for creating a comment with a specific name, comment text, and position.
     *
     * @param name The name of the comment.
     * @param text The text content of the comment.
     * @param p The position of the comment as a {@link Point}.
     */
    public Comment(String name, String text, Point p) {
        this.name = name;
        this.commentText = text;
        this.position = p;
    }

    /**
     * Constructor for creating a comment with default name as "Comment..." and specified text and position.
     *
     * @param text The text content of the comment.
     * @param p The position of the comment as a {@link Point}.
     */
    public Comment(String text, Point p) {
        this.name = "Comment...";
        this.commentText = text;
        this.position = p;
    }

    /**
     * Default constructor for creating an empty comment with a placeholder name and no specific text or position.
     */
    public Comment() {
        this.name = "Comment...";
        this.commentText = "";
        this.position = null;
    }

    /**
     * Gets the name of the comment.
     *
     * @return The name of the comment.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the comment.
     *
     * @param name The name to set for the comment.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the text content of the comment.
     *
     * @return The text content of the comment.
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * Sets the text content of the comment.
     *
     * @param commentText The text to set for the comment.
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    /**
     * Gets the position of the comment in 2D graphical space.
     *
     * @return The position of the comment as a {@link Point}.
     */
    public Point getPosition() {
        return position;
    }

    /**
     * Sets the position of the comment in 2D graphical space.
     *
     * @param position The new position as a {@link Point}.
     */
    public void setPosition(Point position) {
        this.position = position;
    }

    /**
     * Displays the comment. Implementation is currently empty.
     */
    @Override
    public void display() {
    }

    /**
     * Adds a property to the comment. Implementation is currently empty.
     */
    @Override
    public void addProperty() {
    }

    /**
     * Removes a property from the comment. Implementation is currently empty.
     */
    @Override
    public void removeProperty() {
    }

    /**
     * Adds a constraint to the comment. Implementation is currently empty.
     */
    @Override
    public void addConstraint() {
    }

    /**
     * Removes a constraint from the comment. Implementation is currently empty.
     */
    @Override
    public void removeConstraint() {
    }

    /**
     * Gets the type of class for JSON serialization purposes.
     * This method is annotated with {@link JsonProperty} to ensure compatibility during JSON processing,
     * especially for Jackson serialization and deserialization.
     *
     * @return The string representation of the class type, i.e., "Comment".
     */
    @JsonProperty("classType")
    @Override
    public String getClassType() {
        return "Comment";
    }
}
