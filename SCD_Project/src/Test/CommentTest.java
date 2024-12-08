package Test;

import business.Comment;  // Import the Comment class from the business package
import org.junit.jupiter.api.Assertions;  // Import JUnit's Assertions class for writing test assertions
import org.junit.jupiter.api.Test;  // Import JUnit's Test annotation to mark test methods

import java.awt.*;  // Import Java's AWT Point class to use Point objects in tests

import static org.junit.jupiter.api.Assertions.assertNull;  // Import assertion method to check for null values

// Test class for the 'Comment' class in the business package
class CommentTest {

    // Test the default constructor of the 'Comment' class
    @Test
    void testDefaultConstructor() {
        // Create an instance of Comment using the default constructor
        Comment comment = new Comment();

        // Verify the default comment text is an empty string
        Assertions.assertEquals("", comment.getCommentText());

        // Verify the default position is null
        assertNull(comment.getPosition());
    }

    // Test the parameterized constructor of the 'Comment' class
    @Test
    void testParameterizedConstructor() {
        // Create a point to represent the position of the comment
        Point position = new Point(50, 50);

        // Create an instance of Comment using the parameterized constructor
        Comment comment = new Comment("This is a comment", position);

        // Verify that the comment text is set correctly
        Assertions.assertEquals("This is a comment", comment.getCommentText());

        // Verify that the position is set correctly
        Assertions.assertEquals(position, comment.getPosition());
    }

    // Test the getter and setter for 'commentText'
    @Test
    void testGetAndSetCommentText() {
        // Create a new Comment instance
        Comment comment = new Comment();

        // Set the comment text
        comment.setCommentText("New comment text");

        // Verify that the comment text has been set correctly
        Assertions.assertEquals("New comment text", comment.getCommentText());
    }

    // Test the getter and setter for 'position'
    @Test
    void testGetAndSetPosition() {
        // Create a new Comment instance
        Comment comment = new Comment();

        // Create a new position point
        Point newPosition = new Point(100, 200);

        // Set the position for the comment
        comment.setPosition(newPosition);

        // Verify that the position has been set correctly
        Assertions.assertEquals(newPosition, comment.getPosition());
    }

    // Test the method 'getClassType' to verify it returns the correct class type
    @Test
    void testGetClassType() {
        // Create a new Comment instance
        Comment comment = new Comment();

        // Verify that the class type is 'Comment'
        Assertions.assertEquals("Comment", comment.getClassType());
    }

    // Test the 'display' method of the Comment class
    @Test
    void testDisplay() {
        // Create a new Comment instance
        Comment comment = new Comment();

        // Verify that calling 'display' does not throw any exceptions
        Assertions.assertDoesNotThrow(comment::display);
    }

    // Test the 'addProperty' method of the Comment class
    @Test
    void testAddProperty() {
        // Create a new Comment instance
        Comment comment = new Comment();

        // Verify that calling 'addProperty' does not throw any exceptions
        Assertions.assertDoesNotThrow(comment::addProperty);
    }

    // Test the 'removeProperty' method of the Comment class
    @Test
    void testRemoveProperty() {
        // Create a new Comment instance
        Comment comment = new Comment();

        // Verify that calling 'removeProperty' does not throw any exceptions
        Assertions.assertDoesNotThrow(comment::removeProperty);
    }

    // Test the 'addConstraint' method of the Comment class
    @Test
    void testAddConstraint() {
        // Create a new Comment instance
        Comment comment = new Comment();

        // Verify that calling 'addConstraint' does not throw any exceptions
        Assertions.assertDoesNotThrow(comment::addConstraint);
    }

    // Test the 'removeConstraint' method of the Comment class
    @Test
    void testRemoveConstraint() {
        // Create a new Comment instance
        Comment comment = new Comment();

        // Verify that calling 'removeConstraint' does not throw any exceptions
        Assertions.assertDoesNotThrow(comment::removeConstraint);
    }
}
