package Test;

import org.junit.jupiter.api.Assertions;  // Importing Assertions from JUnit for validation
import org.junit.jupiter.api.Test;  // Importing JUnit Test annotation for test methods

import java.awt.*;  // Importing the Point class for representing positions
import java.util.ArrayList;  // Importing ArrayList to handle lists of attributes and methods

import static org.junit.Assert.assertEquals;  // Importing assertEquals for comparing expected and actual values

/**
 * Test class for the 'Class' class from the 'business' package.
 * Contains unit tests to verify the functionality of constructors, getters, setters, and other methods.
 */
class ClassTest {

    /**
     * Tests the default constructor of the 'Class' class.
     * Verifies the initial default values for the name, partitions, position, attributes, and methods.
     */
    @Test
    void testDefaultConstructor() {
        business.Class c1 = new business.Class();
        assertEquals("Class", c1.getName());
        Assertions.assertEquals(0, c1.getNoOfPartitions());
        Assertions.assertEquals(new Point(100, 100), c1.getPosition());
        Assertions.assertTrue(c1.getAttributes().isEmpty());
        Assertions.assertTrue(c1.getMethods().isEmpty());
    }

    /**
     * Tests the parameterized constructor of the 'Class' class.
     * Verifies that the provided name, number of partitions, and position are set correctly.
     */
    @Test
    void testParameterizedConstructor() {
        Point position = new Point(50, 50);
        business.Class c1 = new business.Class("MyClass", 2, true, position);
        assertEquals("MyClass", c1.getName());
        Assertions.assertEquals(2, c1.getNoOfPartitions());
        Assertions.assertEquals(position, c1.getPosition());
        Assertions.assertTrue(c1.getAttributes().isEmpty());
        Assertions.assertTrue(c1.getMethods().isEmpty());
    }

    /**
     * Tests the getter and setter methods for the 'name' attribute.
     */
    @Test
    void testGetAndSetName() {
        business.Class c1 = new business.Class();
        c1.setName("TestName");
        assertEquals("TestName", c1.getName());
    }

    /**
     * Tests the getter and setter methods for the 'position' attribute.
     */
    @Test
    void testGetAndSetPosition() {
        business.Class c1 = new business.Class();
        Point newPosition = new Point(200, 300);
        c1.setPosition(newPosition);
        Assertions.assertEquals(newPosition, c1.getPosition());
    }

    /**
     * Tests the getter and setter methods for the 'number of partitions' attribute.
     */
    @Test
    void testGetAndSetNoOfPartitions() {
        business.Class c1 = new business.Class();
        c1.setNoOfPartitions(3);
        Assertions.assertEquals(3, c1.getNoOfPartitions());
    }

    /**
     * Tests adding an attribute to the 'Class' object.
     */
    @Test
    void testAddAttribute() {
        business.Class c1 = new business.Class();
        c1.addAttribute("attribute1");
        Assertions.assertEquals(1, c1.getAttributes().size());
        Assertions.assertTrue(c1.getAttributes().contains("attribute1"));
    }

    /**
     * Tests adding a method to the 'Class' object.
     */
    @Test
    void testAddMethod() {
        business.Class c1 = new business.Class();
        c1.addMethod("method1");
        Assertions.assertEquals(1, c1.getMethods().size());
        Assertions.assertTrue(c1.getMethods().contains("method1"));
    }

    /**
     * Tests the getter and setter methods for the 'attributes' list.
     */
    @Test
    void testGetAndSetAttributes() {
        business.Class c1 = new business.Class();
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("attr1");
        attributes.add("attr2");
        c1.setAttributes(attributes);
        Assertions.assertEquals(attributes, c1.getAttributes());
    }

    /**
     * Tests the getter and setter methods for the 'methods' list.
     */
    @Test
    void testGetAndSetMethods() {
        business.Class c1 = new business.Class();
        ArrayList<String> methods = new ArrayList<>();
        methods.add("method1");
        methods.add("method2");
        c1.setMethods(methods);
        assertEquals(methods, c1.getMethods());
    }

    /**
     * Tests fetching the diagram notes of the 'Class' object when attributes are set.
     */
    @Test
    void testGetDiagramNotes() {
        business.Class c1 = new business.Class("DemoClass", 2, true, new Point(100, 100));
        c1.addAttribute("desc1");
        String diagramNotes = c1.getDiagramNotes();
        Assertions.assertTrue(diagramNotes.contains("DemoClass"), "Notes should contain the class name");
        Assertions.assertTrue(diagramNotes.contains("desc1"), "Notes should contain the description");
    }

    /**
     * Tests fetching the diagram notes when no attributes or methods are set.
     */
    @Test
    void testGetDiagramNotesWithoutAttributesOrMethods() {
        business.Class c1 = new business.Class("EmptyClass", 2, true, new Point(100, 100));
        String diagramNotes = c1.getDiagramNotes();
        String regex = "EmptyClass\\s*--\\s*--";
        Assertions.assertTrue(diagramNotes.matches("(?s).*" + regex + ".*"),
                "Diagram notes should match the expected structure");
    }

    /**
     * Tests fetching the class type of the 'Class' object.
     */
    @Test
    void testGetClassType() {
        business.Class c1 = new business.Class();
        Assertions.assertEquals("Class", c1.getClassType());
    }

    /**
     * Tests the display method of the 'Class' object to ensure no exceptions are thrown.
     */
    @Test
    void testDisplay() {
        business.Class c1 = new business.Class();
        Assertions.assertDoesNotThrow(c1::display);
    }

    /**
     * Tests the addProperty functionality of the 'Class' object.
     */
    @Test
    void testAddProperty() {
        business.Class c1 = new business.Class();
        Assertions.assertDoesNotThrow(c1::addProperty);
    }

    /**
     * Tests the removeProperty functionality of the 'Class' object.
     */
    @Test
    void testRemoveProperty() {
        business.Class c1 = new business.Class();
        Assertions.assertDoesNotThrow(c1::removeProperty);
    }

    /**
     * Tests the addConstraint functionality of the 'Class' object.
     */
    @Test
    void testAddConstraint() {
        business.Class c1 = new business.Class();
        Assertions.assertDoesNotThrow(c1::addConstraint);
    }

    /**
     * Tests the removeConstraint functionality of the 'Class' object.
     */
    @Test
    void testRemoveConstraint() {
        business.Class c1 = new business.Class();
        Assertions.assertDoesNotThrow(c1::removeConstraint);
    }
}
