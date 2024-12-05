package Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClassTest {

    @Test
    void testDefaultConstructor() {
        Class c1 = new Class();
        assertEquals("Class", c1.getName());
        Assertions.assertEquals(0, c1.getNoOfPartitions());
        Assertions.assertEquals(new Point(100, 100), c1.getPosition());
        Assertions.assertTrue(c1.getAttributes().isEmpty());
        Assertions.assertTrue(c1.getMethods().isEmpty());
    }

    @Test
    void testParameterizedConstructor() {
        Point position = new Point(50, 50);
        Class c1 = new Class("MyClass", 2, true, position);
        assertEquals("MyClass", c1.getName());
        Assertions.assertEquals(2, c1.getNoOfPartitions());
        Assertions.assertEquals(position, c1.getPosition());
        Assertions.assertTrue(c1.getAttributes().isEmpty());
        Assertions.assertTrue(c1.getMethods().isEmpty());
    }

    @Test
    void testGetAndSetName() {
        Class c1 = new Class();
        c1.setName("TestName");
        assertEquals("TestName", c1.getName());
    }

    @Test
    void testGetAndSetPosition() {
        Class c1 = new Class();
        Point newPosition = new Point(200, 300);
        c1.setPosition(newPosition);
        Assertions.assertEquals(newPosition, c1.getPosition());
    }

    @Test
    void testGetAndSetNoOfPartitions() {
        Class c1 = new Class();
        c1.setNoOfPartitions(3);
        Assertions.assertEquals(3, c1.getNoOfPartitions());
    }

    @Test
    void testAddAttribute() {
        Class c1 = new Class();
        c1.addAttribute("attribute1");
        Assertions.assertEquals(1, c1.getAttributes().size());
        Assertions.assertTrue(c1.getAttributes().contains("attribute1"));
    }

    @Test
    void testAddMethod() {
        Class c1 = new Class();
        c1.addMethod("method1");
        Assertions.assertEquals(1, c1.getMethods().size());
        Assertions.assertTrue(c1.getMethods().contains("method1"));
    }

    @Test
    void testGetAndSetAttributes() {
        Class c1 = new Class();
        List<String> attributes = new ArrayList<>();
        attributes.add("attr1");
        attributes.add("attr2");
        c1.setAttributes(attributes);
        Assertions.assertEquals(attributes, c1.getAttributes());
    }

    @Test
    void testGetAndSetMethods() {
        Class c1 = new Class();
        List<String> methods = new ArrayList<>();
        methods.add("method1");
        methods.add("method2");
        c1.setMethods(methods);
        assertEquals(methods, c1.getMethods());
    }

    @Test
    void testGetDiagramNotes() {
        Class c1 = new Class("DemoClass", 2, true, new Point(100, 100));
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
        Class c1 = new Class("EmptyClass", 2, true, new Point(100, 100));
        String expectedNotes = """
                EmptyClass
                --
                --
                """;
        Assertions.assertEquals(expectedNotes, c1.getDiagramNotes());
    }

    @Test
    void testGetClassType() {
        Class c1 = new Class();
        Assertions.assertEquals("Class", c1.getClassType());
    }

    // Test methods for display, addProperty, removeProperty, addConstraint, and removeConstraint
    @Test
    void testDisplay() {
        Class c1 = new Class();
        Assertions.assertDoesNotThrow(c1::display);
    }

    @Test
    void testAddProperty() {
        Class c1 = new Class();
        Assertions.assertDoesNotThrow(c1::addProperty);
    }

    @Test
    void testRemoveProperty() {
        Class c1 = new Class();
        Assertions.assertDoesNotThrow(c1::removeProperty);
    }

    @Test
    void testAddConstraint() {
        Class c1 = new Class();
        Assertions.assertDoesNotThrow(c1::addConstraint);
    }

    @Test
    void testRemoveConstraint() {
        Class c1 = new Class();
        Assertions.assertDoesNotThrow(c1::removeConstraint);
    }
}
