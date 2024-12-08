package Test;

import business.Association;  // Importing the Association class to be tested
import org.junit.jupiter.api.Assertions;  // Importing JUnit Assertions for validating results
import org.junit.jupiter.api.Test;  // Importing JUnit Test annotation for test methods
import java.awt.*;  // Importing the Point class for managing start and end points

import static org.junit.jupiter.api.Assertions.assertNull;  // Importing assertion methods for testing

/**
 * Test class for the Association class.
 * Contains unit tests for various methods and functionalities of the Association class.
 */
class AssociationTest {

    /**
     * Tests the default constructor of the Association class.
     * Verifies that the default values for type, name, start point, and end point are correct.
     */
    @Test
    void testDefaultConstructor() {
        Association association = new Association();
        Assertions.assertEquals("", association.getType());
        Assertions.assertEquals("", association.getName());
        assertNull(association.getStartPoint());
        assertNull(association.getEndPoint());
    }

    /**
     * Tests the parameterized constructor of the Association class.
     * Verifies that the type, name, start point, and end point are set correctly.
     */
    @Test
    void testParameterizedConstructor() {
        Point start = new Point(10, 20);
        Point end = new Point(30, 40);
        Association association = new Association("Dependency", "Assoc1", start, end);
        Assertions.assertEquals("Dependency", association.getType());
        Assertions.assertEquals("Assoc1", association.getName());
        Assertions.assertEquals(start, association.getStartPoint());
        Assertions.assertEquals(end, association.getEndPoint());
    }

    /**
     * Tests the getType method of the Association class.
     * Verifies that the type is retrieved correctly after being set.
     */
    @Test
    void testGetType() {
        Association association = new Association();
        association.setType("AssociationType");
        Assertions.assertEquals("AssociationType", association.getType());
    }

    /**
     * Tests the setType method of the Association class.
     * Verifies that the type is updated correctly.
     */
    @Test
    void testSetType() {
        Association association = new Association();
        association.setType("Generalization");
        Assertions.assertEquals("Generalization", association.getType());
    }

    /**
     * Tests the getName method of the Association class.
     * Verifies that the name is retrieved correctly after being set.
     */
    @Test
    void testGetName() {
        Association association = new Association();
        association.setName("Relation1");
        Assertions.assertEquals("Relation1", association.getName());
    }

    /**
     * Tests the setName method of the Association class.
     * Verifies that the name is updated correctly.
     */
    @Test
    void testSetName() {
        Association association = new Association();
        association.setName("Link1");
        Assertions.assertEquals("Link1", association.getName());
    }

    /**
     * Tests the getStartPoint method of the Association class.
     * Verifies that the start point is retrieved correctly after being set.
     */
    @Test
    void testGetStartPoint() {
        Point start = new Point(5, 10);
        Association association = new Association();
        association.setStartPoint(start);
        Assertions.assertEquals(start, association.getStartPoint());
    }

    /**
     * Tests the setStartPoint method of the Association class.
     * Verifies that the start point is updated correctly.
     */
    @Test
    void testSetStartPoint() {
        Point start = new Point(15, 25);
        Association association = new Association();
        association.setStartPoint(start);
        Assertions.assertEquals(start, association.getStartPoint());
    }

    /**
     * Tests the getEndPoint method of the Association class.
     * Verifies that the end point is retrieved correctly after being set.
     */
    @Test
    void testGetEndPoint() {
        Point end = new Point(20, 30);
        Association association = new Association();
        association.setEndPoint(end);
        Assertions.assertEquals(end, association.getEndPoint());
    }

    /**
     * Tests the setEndPoint method of the Association class.
     * Verifies that the end point is updated correctly.
     */
    @Test
    void testSetEndPoint() {
        Point end = new Point(35, 45);
        Association association = new Association();
        association.setEndPoint(end);
        Assertions.assertEquals(end, association.getEndPoint());
    }

    /**
     * Tests the getClassType method of the Association class.
     * Verifies that the class type is correctly returned as "Association".
     */
    @Test
    void testGetClassType() {
        Association association = new Association();
        Assertions.assertEquals("Association", association.getClassType());
    }

    /**
     * Tests the display method of the Association class.
     * Verifies that calling display does not throw any exceptions.
     */
    @Test
    void testDisplay() {
        Association association = new Association();
        Assertions.assertDoesNotThrow(association::display);
    }

    /**
     * Tests the addProperty method of the Association class.
     * Verifies that calling addProperty does not throw any exceptions.
     */
    @Test
    void testAddProperty() {
        Association association = new Association();
        Assertions.assertDoesNotThrow(association::addProperty);
    }

    /**
     * Tests the removeProperty method of the Association class.
     * Verifies that calling removeProperty does not throw any exceptions.
     */
    @Test
    void testRemoveProperty() {
        Association association = new Association();
        Assertions.assertDoesNotThrow(association::removeProperty);
    }

    /**
     * Tests the addConstraint method of the Association class.
     * Verifies that calling addConstraint does not throw any exceptions.
     */
    @Test
    void testAddConstraint() {
        Association association = new Association();
        Assertions.assertDoesNotThrow(association::addConstraint);
    }

    /**
     * Tests the removeConstraint method of the Association class.
     * Verifies that calling removeConstraint does not throw any exceptions.
     */
    @Test
    void testRemoveConstraint() {
        Association association = new Association();
        Assertions.assertDoesNotThrow(association::removeConstraint);
    }
}
