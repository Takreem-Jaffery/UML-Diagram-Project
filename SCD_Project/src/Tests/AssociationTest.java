package Test;

import business.Association;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertNull;

class AssociationTest {

    @Test
    void testDefaultConstructor() {
        Association association = new Association();
        Assertions.assertEquals("", association.getType());
        Assertions.assertEquals("", association.getName());
        assertNull(association.getStartPoint());
        assertNull(association.getEndPoint());
    }

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

    @Test
    void testGetType() {
        Association association = new Association();
        association.setType("AssociationType");
        Assertions.assertEquals("AssociationType", association.getType());
    }

    @Test
    void testSetType() {
        Association association = new Association();
        association.setType("Generalization");
        Assertions.assertEquals("Generalization", association.getType());
    }

    @Test
    void testGetName() {
        Association association = new Association();
        association.setName("Relation1");
        Assertions.assertEquals("Relation1", association.getName());
    }

    @Test
    void testSetName() {
        Association association = new Association();
        association.setName("Link1");
        Assertions.assertEquals("Link1", association.getName());
    }

    @Test
    void testGetStartPoint() {
        Point start = new Point(5, 10);
        Association association = new Association();
        association.setStartPoint(start);
        Assertions.assertEquals(start, association.getStartPoint());
    }

    @Test
    void testSetStartPoint() {
        Point start = new Point(15, 25);
        Association association = new Association();
        association.setStartPoint(start);
        Assertions.assertEquals(start, association.getStartPoint());
    }

    @Test
    void testGetEndPoint() {
        Point end = new Point(20, 30);
        Association association = new Association();
        association.setEndPoint(end);
        Assertions.assertEquals(end, association.getEndPoint());
    }

    @Test
    void testSetEndPoint() {
        Point end = new Point(35, 45);
        Association association = new Association();
        association.setEndPoint(end);
        Assertions.assertEquals(end, association.getEndPoint());
    }

    @Test
    void testGetClassType() {
        Association association = new Association();
        Assertions.assertEquals("Association", association.getClassType());
    }

    // Test methods for display, addProperty, removeProperty, addConstraint, and removeConstraint
    @Test
    void testDisplay() {
        Association association = new Association();
        Assertions.assertDoesNotThrow(association::display);
    }

    @Test
    void testAddProperty() {
        Association association = new Association();
        Assertions.assertDoesNotThrow(association::addProperty);
    }

    @Test
    void testRemoveProperty() {
        Association association = new Association();
        Assertions.assertDoesNotThrow(association::removeProperty);
    }

    @Test
    void testAddConstraint() {
        Association association = new Association();
        Assertions.assertDoesNotThrow(association::addConstraint);
    }

    @Test
    void testRemoveConstraint() {
        Association association = new Association();
        Assertions.assertDoesNotThrow(association::removeConstraint);
    }
}
