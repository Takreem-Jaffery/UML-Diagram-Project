package Test;

import business.Actor;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertNull;

public class ActorTest {

    @Test
    public void testDefaultConstructor() {
        Actor actor = new Actor();
        Assertions.assertEquals("Actor", actor.getName());
        assertNull(actor.getPosition());
    }

    @Test
    public void testParameterizedConstructor() {
        Point position = new Point(10, 20);
        Actor actor = new Actor("Aneeza", position);
        Assertions.assertEquals("Aneeza", actor.getName());
        Assertions.assertEquals(position, actor.getPosition());
    }

    @Test
    public void  testGetName() {
        Actor actor = new Actor();
        actor.setName("Ali");
        Assertions.assertEquals("Ali", actor.getName());
    }
    @Test
    public void  testSetName() {
        Actor actor = new Actor();
        actor.setName("Basheer");
        Assertions.assertEquals("Basheer", actor.getName());
    }

    @Test
    public void testGetPosition() {
        Point position = new Point(5, 15);
        Actor actor = new Actor();
        actor.setPosition(position);
        Assertions.assertEquals(position, actor.getPosition());
    }

    @Test
    public void testSetPosition() {
        Point position = new Point(7, 14);
        Actor actor = new Actor();
        actor.setPosition(position);
        Assertions.assertEquals(position, actor.getPosition());
    }

    @Test
    public void testGetClassType() {
        Actor actor = new Actor();
        Assertions.assertEquals("Actor", actor.getClassType());
    }

    @Test
    public void testDisplay() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::display);
    }

    @Test
    public void testAddProperty() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::addProperty);
    }

    @Test
    public void testRemoveProperty() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::removeProperty);
    }

    @Test
    public void testAddConstraint() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::addConstraint);
    }

    @Test
    public void testRemoveConstraint() {
        Actor actor = new Actor();
        Assertions.assertDoesNotThrow(actor::removeConstraint);
    }
}
