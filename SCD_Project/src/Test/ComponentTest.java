package Test;

import business.Association;  // Import the Association class from the business package
import business.Comment;      // Import the Comment class from the business package
import business.Class;        // Import the Class class from the business package
import business.Component;    // Import the Component interface/class (assumed to be part of the business package)
import org.junit.jupiter.api.Assertions;  // Import JUnit's Assertions class for writing test assertions
import org.junit.jupiter.api.Test;        // Import JUnit's Test annotation to mark test methods

import java.awt.*;  // Import Java's AWT Point class to use Point objects in tests

// Test class for the 'Component' interface/class and its subclasses (Class, Association, Comment)
class ComponentTest {

    // Test the behavior of component methods for an instance of the 'Class' class
    @Test
    void testComponentMethodsForClass() {
        // Create a component that is a Class instance
        Component component = new Class("TestClass", 2, true, new Point(100, 100));

        // Verify the class type is correctly identified as "Class"
        Assertions.assertEquals("Class", component.getClassType());

        // Verify that calling 'display' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::display);

        // Verify that calling 'addProperty' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::addProperty);

        // Verify that calling 'removeProperty' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::removeProperty);

        // Verify that calling 'addConstraint' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::addConstraint);

        // Verify that calling 'removeConstraint' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::removeConstraint);
    }

    // Test the behavior of component methods for an instance of the 'Association' class
    @Test
    void testComponentMethodsForAssociation() {
        // Create a component that is an Association instance
        Component component = new Association("Dependency", "Assoc1", new Point(50, 50), new Point(200, 200));

        // Verify the class type is correctly identified as "Association"
        Assertions.assertEquals("Association", component.getClassType());

        // Verify that calling 'display' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::display);

        // Verify that calling 'addProperty' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::addProperty);

        // Verify that calling 'removeProperty' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::removeProperty);

        // Verify that calling 'addConstraint' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::addConstraint);

        // Verify that calling 'removeConstraint' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::removeConstraint);
    }

    // Test the behavior of component methods for an instance of the 'Comment' class
    @Test
    void testComponentMethodsForComment() {
        // Create a component that is a Comment instance
        Component component = new Comment("This is a comment", new Point(10, 10));

        // Verify the class type is correctly identified as "Comment"
        Assertions.assertEquals("Comment", component.getClassType());

        // Verify that calling 'display' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::display);

        // Verify that calling 'addProperty' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::addProperty);

        // Verify that calling 'removeProperty' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::removeProperty);

        // Verify that calling 'addConstraint' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::addConstraint);

        // Verify that calling 'removeConstraint' does not throw any exceptions
        Assertions.assertDoesNotThrow(component::removeConstraint);
    }
}
