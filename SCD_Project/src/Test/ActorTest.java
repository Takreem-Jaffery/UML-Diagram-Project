package Test;

import business.Actor;  // Importing the Actor class to be tested
import org.junit.Test;  // Importing JUnit Test annotation for creating test cases
import org.junit.jupiter.api.Assertions;  // Importing JUnit Assertions for asserting results
import java.awt.*;  // Importing the Point class for position handling
import static org.junit.jupiter.api.Assertions.assertNull;  // Importing assertion methods for tests

// Class to test the functionality of the Actor class
public class ActorTest {

    // Test method for the default constructor of the Actor class
    @Test
    public void testDefaultConstructor() {
        // Create an actor using the default constructor
        Actor actor = new Actor();
        // Check that the default name is "Actor"
        Assertions.assertEquals("Actor", actor.getName());
        // Check that the position is null by default
        assertNull(actor.getPosition());
    }

    // Test method for the parameterized constructor of the Actor class
    @Test
    public void testParameterizedConstructor() {
        // Create a new Point object for the position
        Point position = new Point(10, 20);
        // Create an actor using the parameterized constructor
        Actor actor = new Actor("Aneeza", position);
        // Check that the name is set correctly
        Assertions.assertEquals("Aneeza", actor.getName());
        // Check that the position is set correctly
        Assertions.assertEquals(position, actor.getPosition());
    }

    // Test method for getting the name of the actor
    @Test
    public void testGetName() {
        // Create a new actor object
        Actor actor = new Actor();
        // Set a new name for the actor
        actor.setName("Ali");
        // Verify that the name is updated correctly
        Assertions.assertEquals("Ali", actor.getName());
    }

    // Test method for setting the name of the actor
    @Test
    public void testSetName() {
        // Create a new actor object
        Actor actor = new Actor();
        // Set a new name for the actor
        actor.setName("Basheer");
        // Verify that the name is updated correctly
        Assertions.assertEquals("Basheer", actor.getName());
    }

    // Test method for getting the position of the actor
    @Test
    public void testGetPosition() {
        // Create a Point object for the position
        Point position = new Point(5, 15);
        // Create an actor object and set the position
        Actor actor = new Actor();
        actor.setPosition(position);
        // Verify that the position is correctly set
        Assertions.assertEquals(position, actor.getPosition());
    }

    // Test method for setting the position of the actor
    @Test
    public void testSetPosition() {
        // Create a new Point object for the position
        Point position = new Point(7, 14);
        // Create an actor object and set the position
        Actor actor = new Actor();
        actor.setPosition(position);
        // Verify that the position is correctly set
        Assertions.assertEquals(position, actor.getPosition());
    }

    // Test method for checking the class type of the actor
    @Test
    public void testGetClassType() {
        // Create an actor object
        Actor actor = new Actor();
        // Verify that the class type is "Actor"
        Assertions.assertEquals("Actor", actor.getClassType());
    }

    // Test method for testing the display functionality
    @Test
    public void testDisplay() {
        // Create an actor object
        Actor actor = new Actor();
        // Check that calling display does not throw any exceptions
        Assertions.assertDoesNotThrow(actor::display);
    }

    // Test method for testing the addProperty functionality
    @Test
    public void testAddProperty() {
        // Create an actor object
        Actor actor = new Actor();
        // Check that calling addProperty does not throw any exceptions
        Assertions.assertDoesNotThrow(actor::addProperty);
    }

    // Test method for testing the removeProperty functionality
    @Test
    public void testRemoveProperty() {
        // Create an actor object
        Actor actor = new Actor();
        // Check that calling removeProperty does not throw any exceptions
        Assertions.assertDoesNotThrow(actor::removeProperty);
    }

    // Test method for testing the addConstraint functionality
    @Test
    public void testAddConstraint() {
        // Create an actor object
        Actor actor = new Actor();
        // Check that calling addConstraint does not throw any exceptions
        Assertions.assertDoesNotThrow(actor::addConstraint);
    }

    // Test method for testing the removeConstraint functionality
    @Test
    public void testRemoveConstraint() {
        // Create an actor object
        Actor actor = new Actor();
        // Check that calling removeConstraint does not throw any exceptions
        Assertions.assertDoesNotThrow(actor::removeConstraint);
    }
}
