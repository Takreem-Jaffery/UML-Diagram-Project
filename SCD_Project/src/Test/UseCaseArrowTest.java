package Test;

import business.UseCaseArrow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the UseCaseArrow class.
 * This class contains tests to verify the functionality of the UseCaseArrow object,
 * including its properties (name, type, start and end points) and their update functionality.
 */
class UseCaseArrowTest {

    /**
     * Test method for verifying the functionality of the UseCaseArrow class.
     * It checks the initialization of the arrow's properties, updates them, and validates the changes.
     */
    @Test
    void testUseCaseArrow() {

        // Step 1: Initialize the test data with initial values for the arrow's properties
        String initialName = "Arrow1";  // Initial name of the arrow
        String initialType = "Association";  // Initial type of the arrow
        Point initialStartPoint = new Point(10, 20);  // Initial start point of the arrow
        Point initialEndPoint = new Point(30, 40);  // Initial end point of the arrow

        // Step 2: Create a UseCaseArrow object using the initial values
        UseCaseArrow arrow = new UseCaseArrow(initialName, initialType, initialStartPoint, initialEndPoint);

        // Step 3: Retrieve the values from the created arrow object
        String name = arrow.getName();  // Get the name of the arrow
        String type = arrow.getType();  // Get the type of the arrow
        Point startPoint = arrow.getStartPoint();  // Get the start point of the arrow
        Point endPoint = arrow.getEndPoint();  // Get the end point of the arrow
        String classType = arrow.getClassType();  // Get the class type (expected to be "Arrow")

        // Step 4: Validate that the retrieved values match the initial ones using assertions
        assertEquals(initialName, name, "The name should be the same as the initial name.");
        assertEquals(initialType, type, "The type should be the same as the initial type.");
        assertEquals(initialStartPoint, startPoint, "The start point should be the same as the initial start point.");
        assertEquals(initialEndPoint, endPoint, "The end point should be the same as the initial end point.");
        assertEquals("Arrow", classType, "The class type should be 'Arrow'.");  // Expect "Arrow" as the class type

        // Step 5: Change the arrow's properties to new values
        String newName = "Arrow2";  // New name of the arrow
        String newType = "Generalization";  // New type of the arrow
        Point newStartPoint = new Point(50, 60);  // New start point of the arrow
        Point newEndPoint = new Point(70, 80);  // New end point of the arrow

        // Update the arrow's properties using setter methods
        arrow.setName(newName);
        arrow.setType(newType);
        arrow.setStartPoint(newStartPoint);
        arrow.setEndPoint(newEndPoint);

        // Step 6: Validate that the updated values are correctly set using assertions
        Assertions.assertEquals(newName, arrow.getName(), "The name should be updated.");
        Assertions.assertEquals(newType, arrow.getType(), "The type should be updated.");
        Assertions.assertEquals(newStartPoint, arrow.getStartPoint(), "The start point should be updated.");
        Assertions.assertEquals(newEndPoint, arrow.getEndPoint(), "The end point should be updated.");
    }
}
