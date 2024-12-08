package Test;

import business.Actor;  // Importing the Actor class to be tested
import org.junit.Test;  // Importing JUnit Test annotation for creating test cases
import org.junit.jupiter.api.Assertions;  // Importing JUnit Assertions for asserting results
import java.awt.*;  // Importing the Point class for position handling
import static org.junit.jupiter.api.Assertions.assertNull;  // Importing assertion methods for tests

/**
 * Test class for the Actor class.
 * Contains unit tests for various methods and functionalities of the Actor class.
 */
public class ActorTest {

    /**
     * Tests the default constructor of the Actor class.
     * Verifies that the default name is "Actor" and the position is null.
     */
    @Test
    public void testDefaultConstructor() {
        Actor actor = new Actor();
        Assertions.assertEquals("Actor", actor.getName());
        assertNull(actor.getPosition());
    }

    /**
     * Tests the parameterized constructor of the Actor class.
     * Verifies that the name and position are set correctly.
     */
    @Test
    public void testParameterizedConstructor() {
        Point position = new Point(10, 20);
        Actor actor = new Actor("Aneeza", position);
        Assertions.assertEquals("Aneeza", actor.getName());
        Assertions.assertEquals(position, actor.getPosition());
    }

    /**
     * Tests the getName method of the Actor class.
     * Verifies that the name is retrieved correctly after being set.
     */
    @Test
    public void testGetName() {
        Actor actor = new Actor();
        actor.setName("Ali");
        Assertions.assertEquals("Ali", actor.getName());
    }

    /**
     * Tests the setName method of the Actor class.
     * Verifies that the name is updated correctly.
     */
    @Test
    public void testSetName() {
        Actor actor = new Actor();
        actor.setName("Basheer");
        Assertions.assertEquals("Basheer", actor.getName());
    }

    /**
     * Tests the getPosition method of the Actor class.
     * Verifies that the position is retrieved correctly after being set.
     */
    @Test
    public void testGetPosition() {
        Point position = new Point(5, 15);
        Actor actor = new Actor();
        actor.setPosition(position);
        Assertions.assertEquals(position, actor.getPosition());
    }

    /**
     * Tests the setPosition method of the Actor class.
     * Verifies that the position is updated correctly.
     */
    @Test
    public void testSetPosition() {
        Point position = new Point(7, 14);
        Actor actor = new Actor();
        actor.setPosition(position);
        Assertions.assertEquals(position, actor.getPosition());
    }

    /**
     * Tests the getClassType method of the Actor class.
     * Verifies that the class type is "Actor".
     */
    @Test
    public void testGetClassType() {
        Actor actor = new Actor();
        Assertions.assertEquals("Actor", actor.getClassType());
    }

    /**
     * Tests the display method of the Actor class.
     * Verifies that calling display does not throw any exceptions.
     */
    @Test
    public void testDisplay() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::display);
    }

    /**
     * Tests the addProperty method of the Actor class.
     * Verifies that calling addProperty does not throw any exceptions.
     */
    @Test
    public void testAddProperty() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::addProperty);
    }

    /**
     * Tests the removeProperty method of the Actor class.
     * Verifies that calling removeProperty does not throw any exceptions.
     */
    @Test
    public void testRemoveProperty() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::removeProperty);
    }

    /**
     * Tests the addConstraint method of the Actor class.
     * Verifies that calling addConstraint does not throw any exceptions.
     */
    @Test
    public void testAddConstraint() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::addConstraint);
    }

    /**
     * Tests the removeConstraint method of the Actor class.
     * Verifies that calling removeConstraint does not throw any exceptions.
     */
    @Test
    public void testRemoveConstraint() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::removeConstraint);
    }
}
