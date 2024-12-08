package Test;

import business.Usecase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the Usecase class.
 * This class contains tests to verify the functionality of the Usecase class,
 * including checking the name, position, and diagram notes.
 */
class UsecaseTest {

    /**
     * Test method for verifying the functionality of the Usecase class.
     * This method tests the creation, updating, and validation of a Usecase object.
     */
    @Test
    void testUsecase() {

        // Step 1: Initialize variables with initial values for testing
        String initialName = "TestUsecase";
        Point initialPosition = new Point(50, 100);

        // Step 2: Create a new Usecase object with the initial name and position
        Usecase usecase = new Usecase(initialName, initialPosition);

        // Step 3: Retrieve the name, position, and diagram notes of the created usecase
        String name = usecase.getName();
        Point position = usecase.getPosition();
        String diagramNotes = usecase.getDiagramNotes();

        // Step 4: Assert that the initial values match the expected values
        assertEquals(initialName, name, "The name should be the same as the initial name.");
        assertEquals(initialPosition, position, "The position should be the same as the initial position.");
        assertEquals(initialName + "\n", diagramNotes, "The diagram notes should contain the name of the usecase.");

        // Step 5: Update the name and position of the usecase object
        String newName = "UpdatedUsecase";
        Point newPosition = new Point(150, 200);
        usecase.setName(newName);
        usecase.setPosition(newPosition);

        // Step 6: Assert that the updated name and position are correctly set
        Assertions.assertEquals(newName, usecase.getName(), "The name should be updated.");
        Assertions.assertEquals(newPosition, usecase.getPosition(), "The position should be updated.");

        // Step 7: Assert that the diagram notes are updated with the new name
        Assertions.assertEquals(newName + "\n", usecase.getDiagramNotes(), "The diagram notes should reflect the updated name.");
    }
}
