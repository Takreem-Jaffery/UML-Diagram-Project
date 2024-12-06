package Test;

import business.UseCaseArrow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UseCaseArrowTest {

    @Test
    void testUseCaseArrow() {

        String initialName = "Arrow1";
        String initialType = "Association";
        Point initialStartPoint = new Point(10, 20);
        Point initialEndPoint = new Point(30, 40);
        UseCaseArrow arrow = new UseCaseArrow(initialName, initialType, initialStartPoint, initialEndPoint);

        String name = arrow.getName();
        String type = arrow.getType();
        Point startPoint = arrow.getStartPoint();
        Point endPoint = arrow.getEndPoint();
        String classType = arrow.getClassType();

        assertEquals(initialName, name, "The name should be the same as the initial name.");
        assertEquals(initialType, type, "The type should be the same as the initial type.");
        assertEquals(initialStartPoint, startPoint, "The start point should be the same as the initial start point.");
        assertEquals(initialEndPoint, endPoint, "The end point should be the same as the initial end point.");
        assertEquals("Arrow", classType, "The class type should be 'Arrow'.");

        String newName = "Arrow2";
        String newType = "Generalization";
        Point newStartPoint = new Point(50, 60);
        Point newEndPoint = new Point(70, 80);
        arrow.setName(newName);
        arrow.setType(newType);
        arrow.setStartPoint(newStartPoint);
        arrow.setEndPoint(newEndPoint);

        Assertions.assertEquals(newName, arrow.getName(), "The name should be updated.");
        Assertions.assertEquals(newType, arrow.getType(), "The type should be updated.");
        Assertions.assertEquals(newStartPoint, arrow.getStartPoint(), "The start point should be updated.");
        Assertions.assertEquals(newEndPoint, arrow.getEndPoint(), "The end point should be updated.");
    }
}
