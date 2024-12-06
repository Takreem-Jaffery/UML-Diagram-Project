package Test;

import business.Association;
import business.Comment;
import business.Class;
import business.Component;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

class ComponentTest {

    @Test
    void testComponentMethodsForClass() {
        Component component = new Class("TestClass", 2, true, new Point(100, 100));
        Assertions.assertEquals("Class", component.getClassType());
        Assertions.assertDoesNotThrow(component::display);
        Assertions.assertDoesNotThrow(component::addProperty);
        Assertions.assertDoesNotThrow(component::removeProperty);
        Assertions.assertDoesNotThrow(component::addConstraint);
        Assertions.assertDoesNotThrow(component::removeConstraint);
    }

    @Test
    void testComponentMethodsForAssociation() {
        Component component = new Association("Dependency", "Assoc1", new Point(50, 50), new Point(200, 200));
        Assertions.assertEquals("Association", component.getClassType());
        Assertions.assertDoesNotThrow(component::display);
        Assertions.assertDoesNotThrow(component::addProperty);
        Assertions.assertDoesNotThrow(component::removeProperty);
        Assertions.assertDoesNotThrow(component::addConstraint);
        Assertions.assertDoesNotThrow(component::removeConstraint);
    }

    @Test
    void testComponentMethodsForComment() {
        Component component = new Comment("This is a comment", new Point(10, 10));
        Assertions.assertEquals("Comment", component.getClassType());
        Assertions.assertDoesNotThrow(component::display);
        Assertions.assertDoesNotThrow(component::addProperty);
        Assertions.assertDoesNotThrow(component::removeProperty);
        Assertions.assertDoesNotThrow(component::addConstraint);
        Assertions.assertDoesNotThrow(component::removeConstraint);
    }
}
