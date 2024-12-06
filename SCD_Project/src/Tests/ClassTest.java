package Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

class ClassTest {

    @Test
    void testDefaultConstructor() {
        business.Class c1 = new business.Class();
        assertEquals("Class", c1.getName());
        Assertions.assertEquals(0, c1.getNoOfPartitions());
        Assertions.assertEquals(new Point(100, 100), c1.getPosition());
        Assertions.assertTrue(c1.getAttributes().isEmpty());
        Assertions.assertTrue(c1.getMethods().isEmpty());
    }

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

    @Test
    void testGetAndSetName() {
        business.Class c1 = new business.Class();
        c1.setName("TestName");
        assertEquals("TestName", c1.getName());
    }

    @Test
    void testGetAndSetPosition() {
        business.Class c1 = new business.Class();
        Point newPosition = new Point(200, 300);
        c1.setPosition(newPosition);
        Assertions.assertEquals(newPosition, c1.getPosition());
    }

    @Test
    void testGetAndSetNoOfPartitions() {
        business.Class c1 = new business.Class();
        c1.setNoOfPartitions(3);
        Assertions.assertEquals(3, c1.getNoOfPartitions());
    }

    @Test
    void testAddAttribute() {
        business.Class c1 = new business.Class();
        c1.addAttribute("attribute1");
        Assertions.assertEquals(1, c1.getAttributes().size());
        Assertions.assertTrue(c1.getAttributes().contains("attribute1"));
    }

    @Test
    void testAddMethod() {
        business.Class c1 = new business.Class();
        c1.addMethod("method1");
        Assertions.assertEquals(1, c1.getMethods().size());
        Assertions.assertTrue(c1.getMethods().contains("method1"));
    }

    @Test
    void testGetAndSetAttributes() {
        business.Class c1 = new business.Class();
        ArrayList<String> attributes = new ArrayList<>();
        attributes.add("attr1");
        attributes.add("attr2");
        c1.setAttributes(attributes);
        Assertions.assertEquals(attributes, c1.getAttributes());
    }

    @Test
    void testGetAndSetMethods() {
        business.Class c1 = new business.Class();
        ArrayList<String> methods = new ArrayList<>();
        methods.add("method1");
        methods.add("method2");
        c1.setMethods(methods);
        assertEquals(methods, c1.getMethods());
    }

    @Test
    void testGetDiagramNotes() {
        business.Class c1 = new business.Class("DemoClass", 2, true, new Point(100, 100));
        c1.addAttribute("attr1");
        c1.addMethod("method1");
        String expectedNotes = """
                DemoClass
                --
                attr1
                --
                method1
                """;
        Assertions.assertEquals(expectedNotes, c1.getDiagramNotes());
    }

    @Test
    void testGetDiagramNotesWithoutAttributesOrMethods() {
        business.Class c1 = new business.Class("EmptyClass", 2, true, new Point(100, 100));
        String expectedNotes = """
                EmptyClass
                --
                --
                """;
        Assertions.assertEquals(expectedNotes, c1.getDiagramNotes());
    }

    @Test
    void testGetClassType() {
        business.Class c1 = new business.Class();
        Assertions.assertEquals("Class", c1.getClassType());
    }

    // Test methods for display, addProperty, removeProperty, addConstraint, and removeConstraint
    @Test
    void testDisplay() {
        business.Class c1 = new business.Class();
        Assertions.assertDoesNotThrow(c1::display);
    }

    @Test
    void testAddProperty() {
        business.Class c1 = new business.Class();
        Assertions.assertDoesNotThrow(c1::addProperty);
    }

    @Test
    void testRemoveProperty() {
        business.Class c1 = new business.Class();
        Assertions.assertDoesNotThrow(c1::removeProperty);
    }

    @Test
    void testAddConstraint() {
        business.Class c1 = new business.Class();
        Assertions.assertDoesNotThrow(c1::addConstraint);
    }

    @Test
    void testRemoveConstraint() {
        business.Class c1 = new business.Class();
        Assertions.assertDoesNotThrow(c1::removeConstraint);
    }
}
