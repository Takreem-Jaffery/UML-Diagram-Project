package Test;

import org.junit.jupiter.api.Assertions;  // Importing Assertions from JUnit for validation
import org.junit.jupiter.api.Test;  // Importing JUnit Test annotation for test methods

import java.awt.*;  // Importing the Point class for representing positions
import java.util.ArrayList;  // Importing ArrayList to handle lists of attributes and methods

import static org.junit.Assert.assertEquals;  // Importing assertEquals for comparing expected and actual values

// Test class for verifying the functionality of the 'Class' class from the 'business' package
class ClassTest {

    // Test method for verifying the default constructor of the 'Class' class
    @Test
    void testDefaultConstructor() {
        // Create a 'Class' object using the default constructor
        business.Class c1 = new business.Class();

        // Verify the default name of the class is "Class"
        assertEquals("Class", c1.getName());
        // Verify the default number of partitions is 0
        Assertions.assertEquals(0, c1.getNoOfPartitions());
        // Verify the default position is (100, 100)
        Assertions.assertEquals(new Point(100, 100), c1.getPosition());
        // Verify the attributes list is empty by default
        Assertions.assertTrue(c1.getAttributes().isEmpty());
        // Verify the methods list is empty by default
        Assertions.assertTrue(c1.getMethods().isEmpty());
    }

    // Test method for verifying the parameterized constructor of the 'Class' class
    @Test
    void testParameterizedConstructor() {
        // Create a 'Point' object representing the position
        Point position = new Point(50, 50);
        // Create a 'Class' object using the parameterized constructor
        business.Class c1 = new business.Class("MyClass", 2, true, position);

        // Verify the name of the class is set correctly
        assertEquals("MyClass", c1.getName());
        // Verify the number of partitions is set correctly
        Assertions.assertEquals(2, c1.getNoOfPartitions());
        // Verify the position is set correctly
        Assertions.assertEquals(position, c1.getPosition());
        // Verify the attributes list is empty
        Assertions.assertTrue(c1.getAttributes().isEmpty());
        // Verify the methods list is empty
        Assertions.assertTrue(c1.getMethods().isEmpty());
    }

    // Test method for verifying the getter and setter of the name attribute
    @Test
    void testGetAndSetName() {
        business.Class c1 = new business.Class();
        c1.setName("TestName");
        // Verify the name is correctly set
        assertEquals("TestName", c1.getName());
    }

    // Test method for verifying the getter and setter of the position attribute
    @Test
    void testGetAndSetPosition() {
        business.Class c1 = new business.Class();
        Point newPosition = new Point(200, 300);
        c1.setPosition(newPosition);
        // Verify the position is correctly set
        Assertions.assertEquals(newPosition, c1.getPosition());
    }

    // Test method for verifying the getter and setter of the number of partitions attribute
    @Test
    void testGetAndSetNoOfPartitions() {
        business.Class c1 = new business.Class();
        c1.setNoOfPartitions(3);
        // Verify the number of partitions is correctly set
        Assertions.assertEquals(3, c1.getNoOfPartitions());
    }

    // Test method for adding an attribute to the 'Class' object
    @Test
    void testAddAttribute() {
        business.Class c1 = new business.Class();
        c1.addAttribute("attribute1");
        // Verify the attribute was added successfully
        Assertions.assertEquals(1, c1.getAttributes().size());
        Assertions.assertTrue(c1.getAttributes().contains("attribute1"));
    }

    // Test method for adding a method to the 'Class' object
    @Test
    void testAddMethod() {
        business.Class c1 = new business.Class();
        c1.addMethod("method1");
        // Verify the method was added successfully
        Assertions.assertEquals(1, c1.getMethods().size());
        Assertions.assertTrue(c1.getMethods().contains("method1"));
    }

    // Test method for getting and setting the list of attributes of the 'Class' object
    @Test
    void testGetAndSetAttributes() {
        business.Class c1 = new business.Class();
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("attr1");
        attributes.add("attr2");
        c1.setAttributes(attributes);
        // Verify the list of attributes is correctly set
        Assertions.assertEquals(attributes, c1.getAttributes());
    }

    // Test method for getting and setting the list of methods of the 'Class' object
    @Test
    void testGetAndSetMethods() {
        business.Class c1 = new business.Class();
        ArrayList<String> methods = new ArrayList<>();
        methods.add("method1");
        methods.add("method2");
        c1.setMethods(methods);
        // Verify the list of methods is correctly set
        assertEquals(methods, c1.getMethods());
    }

    // Test method for fetching the diagram notes of the 'Class' object
    @Test
    void testGetDiagramNotes() {
        business.Class c1 = new business.Class("DemoClass", 2, true, new Point(100, 100));
        c1.addAttribute("desc1");

        // Fetch the diagram notes
        String diagramNotes = c1.getDiagramNotes();

        System.out.println("Diagram Notes: \n" + diagramNotes);

        // Verify that the diagram notes contain the class name and description
        Assertions.assertTrue(diagramNotes.contains("DemoClass"), "Notes should contain the class name");
        Assertions.assertTrue(diagramNotes.contains("desc1"), "Notes should contain the description");
    }

    // Test method for verifying diagram notes when there are no attributes or methods
    @Test
    void testGetDiagramNotesWithoutAttributesOrMethods() {
        business.Class c1 = new business.Class("EmptyClass", 2, true, new Point(100, 100));

        String diagramNotes = c1.getDiagramNotes();

        // Define the expected structure of the diagram notes (class name, separators, no attributes/methods)
        String regex = "EmptyClass\\s*--\\s*--";

        // Assert that the diagram notes match the expected structure
        Assertions.assertTrue(diagramNotes.matches("(?s).*" + regex + ".*"), "Diagram notes should match the expected structure");
    }

    // Test method for verifying the class type of the 'Class' object
    @Test
    void testGetClassType() {
        business.Class c1 = new business.Class();
        // Verify the class type is "Class"
        Assertions.assertEquals("Class", c1.getClassType());
    }

    // Test method for verifying the display functionality of the 'Class' object
    @Test
    void testDisplay() {
        business.Class c1 = new business.Class();
        // Verify that calling the display method does not throw any exceptions
        Assertions.assertDoesNotThrow(c1::display);
    }

    // Test method for verifying the addProperty functionality
    @Test
    void testAddProperty() {
        business.Class c1 = new business.Class();
        // Verify that adding a property does not throw any exceptions
        Assertions.assertDoesNotThrow(c1::addProperty);
    }

    // Test method for verifying the removeProperty functionality
    @Test
    void testRemoveProperty() {
        business.Class c1 = new business.Class();
        // Verify that removing a property does not throw any exceptions
        Assertions.assertDoesNotThrow(c1::removeProperty);
    }

    // Test method for verifying the addConstraint functionality
    @Test
    void testAddConstraint() {
        business.Class c1 = new business.Class();
        // Verify that adding a constraint does not throw any exceptions
        Assertions.assertDoesNotThrow(c1::addConstraint);
    }

    // Test method for verifying the removeConstraint functionality
    @Test
    void testRemoveConstraint() {
        business.Class c1 = new business.Class();
        // Verify that removing a constraint does not throw any exceptions
        Assertions.assertDoesNotThrow(c1::removeConstraint);
    }
}
