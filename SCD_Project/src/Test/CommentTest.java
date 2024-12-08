package Test;

import business.Comment;  // Import the Comment class from the business package
import org.junit.jupiter.api.Assertions;  // Import JUnit's Assertions class for writing test assertions
import org.junit.jupiter.api.Test;  // Import JUnit's Test annotation to mark test methods

import java.awt.*;  // Import Java's AWT Point class to use Point objects in tests

import static org.junit.jupiter.api.Assertions.assertNull;  // Import assertion method to check for null values

/**
 * Test class for the 'Comment' class in the business package.
 * This class verifies the behavior of constructors, getters, setters, and additional methods.
 */
class CommentTest {

    /**
     * Test the default constructor of the 'Comment' class.
     */
    @Test
    void testDefaultConstructor() {
        Comment comment = new Comment();

        // Verify default comment text
        Assertions.assertEquals("", comment.getCommentText(), "Default comment text should be an empty string.");

        // Verify default position
        assertNull(comment.getPosition(), "Default position should be null.");
    }

    /**
     * Test the parameterized constructor of the 'Comment' class.
     */
    @Test
    void testParameterizedConstructor() {
        Point position = new Point(50, 50);
        Comment comment = new Comment("This is a comment", position);

        // Verify comment text
        Assertions.assertEquals("This is a comment", comment.getCommentText(), "Comment text should match the provided value.");

        // Verify position
        Assertions.assertEquals(position, comment.getPosition(), "Position should match the provided Point object.");
    }

    /**
     * Test the getter and setter for 'commentText'.
     */
    @Test
    void testGetAndSetCommentText() {
        Comment comment = new Comment();

        // Set and verify comment text
        comment.setCommentText("New comment text");
        Assertions.assertEquals("New comment text", comment.getCommentText(), "Setter should correctly update the comment text.");
    }

    /**
     * Test the getter and setter for 'position'.
     */
    @Test
    void testGetAndSetPosition() {
        Comment comment = new Comment();
        Point newPosition = new Point(100, 200);

        // Set and verify position
        comment.setPosition(newPosition);
        Assertions.assertEquals(newPosition, comment.getPosition(), "Setter should correctly update the position.");
    }

    /**
     * Test the 'getClassType' method to verify it returns the correct class type.
     */
    @Test
    void testGetClassType() {
        Comment comment = new Comment();
        Assertions.assertEquals("Comment", comment.getClassType(), "getClassType should return 'Comment'.");
    }

    /**
     * Test the 'display' method to ensure it runs without throwing exceptions.
     */
    @Test
    void testDisplay() {
        Comment comment = new Comment();
        Assertions.assertDoesNotThrow(comment::display, "display method should not throw any exceptions.");
    }

    /**
     * Test the 'addProperty' method to ensure it runs without throwing exceptions.
     */
    @Test
    void testAddProperty() {
        Comment comment = new Comment();
        Assertions.assertDoesNotThrow(comment::addProperty, "addProperty method should not throw any exceptions.");
    }

    /**
     * Test the 'removeProperty' method to ensure it runs without throwing exceptions.
     */
    @Test
    void testRemoveProperty() {
        Comment comment = new Comment();
        Assertions.assertDoesNotThrow(comment::removeProperty, "removeProperty method should not throw any exceptions.");
    }

    /**
     * Test the 'addConstraint' method to ensure it runs without throwing exceptions.
     */
    @Test
    void testAddConstraint() {
        Comment comment = new Comment();
        Assertions.assertDoesNotThrow(comment::addConstraint, "addConstraint method should not throw any exceptions.");
    }

    /**
     * Test the 'removeConstraint' method to ensure it runs without throwing exceptions.
     */
    @Test
    void testRemoveConstraint() {
        Comment comment = new Comment();
        Assertions.assertDoesNotThrow(comment::removeConstraint, "removeConstraint method should not throw any exceptions.");
    }
}
