package Test;

import business.Association;  // Importing the Association class to be tested
import org.junit.jupiter.api.Assertions;  // Importing JUnit Assertions for validating results
import org.junit.jupiter.api.Test;  // Importing JUnit Test annotation for test methods
import java.awt.*;  // Importing the Point class for managing start and end points

import static org.junit.jupiter.api.Assertions.assertNull;  // Importing assertion methods for testing

// Test class for verifying functionality of the Association class
class AssociationTest {

    // Test method for the default constructor of the Association class
    @Test
    void testDefaultConstructor() {
        // Create an Association object using the default constructor
        Association association = new Association();
        // Verify that the default type is an empty string
        Assertions.assertEquals("", association.getType());
        // Verify that the default name is an empty string
        Assertions.assertEquals("", association.getName());
        // Verify that the default start point is null
        assertNull(association.getStartPoint());
        // Verify that the default end point is null
        assertNull(association.getEndPoint());
    }

    // Test method for the parameterized constructor of the Association class
    @Test
    void testParameterizedConstructor() {
        // Create Point objects for the start and end points
        Point start = new Point(10, 20);
        Point end = new Point(30, 40);
        // Create an Association object using the parameterized constructor
        Association association = new Association("Dependency", "Assoc1", start, end);
        // Verify that the type is set correctly
        Assertions.assertEquals("Dependency", association.getType());
        // Verify that the name is set correctly
        Assertions.assertEquals("Assoc1", association.getName());
        // Verify that the start point is set correctly
        Assertions.assertEquals(start, association.getStartPoint());
        // Verify that the end point is set correctly
        Assertions.assertEquals(end, association.getEndPoint());
    }

    // Test method for getting the type of the association
    @Test
    void testGetType() {
        // Create an Association object using the default constructor
        Association association = new Association();
        // Set the type of the association
        association.setType("AssociationType");
        // Verify that the type is correctly updated
        Assertions.assertEquals("AssociationType", association.getType());
    }

    // Test method for setting the type of the association
    @Test
    void testSetType() {
        // Create an Association object
        Association association = new Association();
        // Set a new type for the association
        association.setType("Generalization");
        // Verify that the type is correctly set
        Assertions.assertEquals("Generalization", association.getType());
    }

    // Test method for getting the name of the association
    @Test
    void testGetName() {
        // Create an Association object
        Association association = new Association();
        // Set a new name for the association
        association.setName("Relation1");
        // Verify that the name is correctly set
        Assertions.assertEquals("Relation1", association.getName());
    }

    // Test method for setting the name of the association
    @Test
    void testSetName() {
        // Create an Association object
        Association association = new Association();
        // Set a new name for the association
        association.setName("Link1");
        // Verify that the name is correctly set
        Assertions.assertEquals("Link1", association.getName());
    }

    // Test method for getting the start point of the association
    @Test
    void testGetStartPoint() {
        // Create a Point object for the start point
        Point start = new Point(5, 10);
        // Create an Association object and set the start point
        Association association = new Association();
        association.setStartPoint(start);
        // Verify that the start point is correctly set
        Assertions.assertEquals(start, association.getStartPoint());
    }

    // Test method for setting the start point of the association
    @Test
    void testSetStartPoint() {
        // Create a Point object for the start point
        Point start = new Point(15, 25);
        // Create an Association object and set the start point
        Association association = new Association();
        association.setStartPoint(start);
        // Verify that the start point is correctly set
        Assertions.assertEquals(start, association.getStartPoint());
    }

    // Test method for getting the end point of the association
    @Test
    void testGetEndPoint() {
        // Create a Point object for the end point
        Point end = new Point(20, 30);
        // Create an Association object and set the end point
        Association association = new Association();
        association.setEndPoint(end);
        // Verify that the end point is correctly set
        Assertions.assertEquals(end, association.getEndPoint());
    }

    // Test method for setting the end point of the association
    @Test
    void testSetEndPoint() {
        // Create a Point object for the end point
        Point end = new Point(35, 45);
        // Create an Association object and set the end point
        Association association = new Association();
        association.setEndPoint(end);
        // Verify that the end point is correctly set
        Assertions.assertEquals(end, association.getEndPoint());
    }

    // Test method for checking the class type of the association
    @Test
    void testGetClassType() {
        // Create an Association object
        Association association = new Association();
        // Verify that the class type is "Association"
        Assertions.assertEquals("Association", association.getClassType());
    }

    // Test methods for display, addProperty, removeProperty, addConstraint, and removeConstraint

    // Test method for the display functionality of the association
    @Test
    void testDisplay() {
        // Create an Association object
        Association association = new Association();
        // Check that calling display does not throw any exceptions
        Assertions.assertDoesNotThrow(association::display);
    }

    // Test method for adding a property to the association
    @Test
    void testAddProperty() {
        // Create an Association object
        Association association = new Association();
        // Check that calling addProperty does not throw any exceptions
        Assertions.assertDoesNotThrow(association::addProperty);
    }

    // Test method for removing a property from the association
    @Test
    void testRemoveProperty() {
        // Create an Association object
        Association association = new Association();
        // Check that calling removeProperty does not throw any exceptions
        Assertions.assertDoesNotThrow(association::removeProperty);
    }

    // Test method for adding a constraint to the association
    @Test
    void testAddConstraint() {
        // Create an Association object
        Association association = new Association();
        // Check that calling addConstraint does not throw any exceptions
        Assertions.assertDoesNotThrow(association::addConstraint);
    }

    // Test method for removing a constraint from the association
    @Test
    void testRemoveConstraint() {
        // Create an Association object
        Association association = new Association();
        // Check that calling removeConstraint does not throw any exceptions
        Assertions.assertDoesNotThrow(association::removeConstraint);
    }
}
