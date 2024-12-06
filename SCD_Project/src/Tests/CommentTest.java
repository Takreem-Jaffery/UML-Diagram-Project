package Test;

import business.Comment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertNull;

class CommentTest {

    @Test
    void testDefaultConstructor() {
        Comment comment = new Comment();
        Assertions.assertEquals("", comment.getCommentText());
        assertNull(comment.getPosition());
    }

    @Test
    void testParameterizedConstructor() {
        Point position = new Point(50, 50);
        Comment comment = new Comment("This is a comment", position);
        Assertions.assertEquals("This is a comment", comment.getCommentText());
        Assertions.assertEquals(position, comment.getPosition());
    }

    @Test
    void testGetAndSetCommentText() {
        Comment comment = new Comment();
        comment.setCommentText("New comment text");
        Assertions.assertEquals("New comment text", comment.getCommentText());
    }

    @Test
    void testGetAndSetPosition() {
        Comment comment = new Comment();
        Point newPosition = new Point(100, 200);
        comment.setPosition(newPosition);
        Assertions.assertEquals(newPosition, comment.getPosition());
    }

    @Test
    void testGetClassType() {
        Comment comment = new Comment();
        Assertions.assertEquals("Comment", comment.getClassType());
    }

    // Test methods for display, addProperty, removeProperty, addConstraint, and removeConstraint
    @Test
    void testDisplay() {
        Comment comment = new Comment();
        Assertions.assertDoesNotThrow(comment::display);
    }

    @Test
    void testAddProperty() {
        Comment comment = new Comment();
        Assertions.assertDoesNotThrow(comment::addProperty);
    }

    @Test
    void testRemoveProperty() {
        Comment comment = new Comment();
        Assertions.assertDoesNotThrow(comment::removeProperty);
    }

    @Test
    void testAddConstraint() {
        Comment comment = new Comment();
        Assertions.assertDoesNotThrow(comment::addConstraint);
    }

    @Test
    void testRemoveConstraint() {
        Comment comment = new Comment();
        Assertions.assertDoesNotThrow(comment::removeConstraint);
    }
}
