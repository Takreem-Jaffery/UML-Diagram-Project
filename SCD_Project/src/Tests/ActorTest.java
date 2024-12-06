package Test;

import business.Actor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertNull;

class ActorTest {

    @Test
    void testDefaultConstructor() {
        Actor actor = new Actor();
        Assertions.assertEquals("Actor", actor.getName());
        assertNull(actor.getPosition());
    }

    @Test
    void testParameterizedConstructor() {
        Point position = new Point(10, 20);
        Actor actor = new Actor("Aneeza", position);
        Assertions.assertEquals("Aneeza", actor.getName());
        Assertions.assertEquals(position, actor.getPosition());
    }

    @Test
    void testGetName() {
        Actor actor = new Actor();
        actor.setName("Ali");
        Assertions.assertEquals("Ali", actor.getName());
    }

    @Test
    void testSetName() {
        Actor actor = new Actor();
        actor.setName("Basheer");
        Assertions.assertEquals("Basheer", actor.getName());
    }

    @Test
    void testGetPosition() {
        Point position = new Point(5, 15);
        Actor actor = new Actor();
        actor.setPosition(position);
        Assertions.assertEquals(position, actor.getPosition());
    }

    @Test
    void testSetPosition() {
        Point position = new Point(7, 14);
        Actor actor = new Actor();
        actor.setPosition(position);
        Assertions.assertEquals(position, actor.getPosition());
    }

    @Test
    void testGetClassType() {
        Actor actor = new Actor();
        Assertions.assertEquals("Actor", actor.getClassType());
    }

    @Test
    void testDisplay() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::display);
    }

    @Test
    void testAddProperty() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::addProperty);
    }

    @Test
    void testRemoveProperty() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::removeProperty);
    }

    @Test
    void testAddConstraint() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::addConstraint);
    }

    @Test
    void testRemoveConstraint() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::removeConstraint);
    }
}
