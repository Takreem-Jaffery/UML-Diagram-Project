package Test;

import business.Association;  // Import the Association class from the business package
import business.Comment;      // Import the Comment class from the business package
import business.Class;        // Import the Class class from the business package
import business.Component;    // Import the Component interface/class from the business package
import org.junit.jupiter.api.Assertions;  // Import JUnit's Assertions class for writing test assertions
import org.junit.jupiter.api.Test;        // Import JUnit's Test annotation to mark test methods

import java.awt.*;  // Import Java's AWT Point class to use Point objects in tests

/**
 * Test class for the 'Component' interface/class and its concrete subclasses ('Class', 'Association', 'Comment').
 */
class ComponentTest {

    /**
     * Test the behavior of 'Component' methods for a 'Class' instance.
     */
    @Test
    void testComponentMethodsForClass() {
        // Create a 'Class' instance as a 'Component'
        Component component = new Class("TestClass", 2, true, new Point(100, 100));

        // Verify the class type
        Assertions.assertEquals("Class", component.getClassType(), "Component class type should be 'Class'.");

        // Verify that methods do not throw exceptions
        Assertions.assertDoesNotThrow(component::display, "Display method should not throw exceptions.");
        Assertions.assertDoesNotThrow(component::addProperty, "addProperty method should not throw exceptions.");
        Assertions.assertDoesNotThrow(component::removeProperty, "removeProperty method should not throw exceptions.");
        Assertions.assertDoesNotThrow(component::addConstraint, "addConstraint method should not throw exceptions.");
        Assertions.assertDoesNotThrow(component::removeConstraint, "removeConstraint method should not throw exceptions.");
    }

    /**
     * Test the behavior of 'Component' methods for an 'Association' instance.
     */
    @Test
    void testComponentMethodsForAssociation() {
        // Create an 'Association' instance as a 'Component'
        Component component = new Association("Dependency", "Assoc1", new Point(50, 50), new Point(200, 200));

        // Verify the class type
        Assertions.assertEquals("Association", component.getClassType(), "Component class type should be 'Association'.");

        // Verify that methods do not throw exceptions
        Assertions.assertDoesNotThrow(component::display, "Display method should not throw exceptions.");
        Assertions.assertDoesNotThrow(component::addProperty, "addProperty method should not throw exceptions.");
        Assertions.assertDoesNotThrow(component::removeProperty, "removeProperty method should not throw exceptions.");
        Assertions.assertDoesNotThrow(component::addConstraint, "addConstraint method should not throw exceptions.");
        Assertions.assertDoesNotThrow(component::removeConstraint, "removeConstraint method should not throw exceptions.");
    }

    /**
     * Test the behavior of 'Component' methods for a 'Comment' instance.
     */
    @Test
    void testComponentMethodsForComment() {
        // Create a 'Comment' instance as a 'Component'
        Component component = new Comment("This is a comment", new Point(10, 10));

        // Verify the class type
        Assertions.assertEquals("Comment", component.getClassType(), "Component class type should be 'Comment'.");

        // Verify that methods do not throw exceptions
        Assertions.assertDoesNotThrow(component::display, "Display method should not throw exceptions.");
        Assertions.assertDoesNotThrow(component::addProperty, "addProperty method should not throw exceptions.");
        Assertions.assertDoesNotThrow(component::removeProperty, "removeProperty method should not throw exceptions.");
        Assertions.assertDoesNotThrow(component::addConstraint, "addConstraint method should not throw exceptions.");
        Assertions.assertDoesNotThrow(component::removeConstraint, "removeConstraint method should not throw exceptions.");
    }
}
