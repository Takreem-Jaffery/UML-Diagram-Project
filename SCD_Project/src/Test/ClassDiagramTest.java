package Test;

import business.ClassDiagram;  // Importing the ClassDiagram class to be tested
import org.junit.jupiter.api.Assertions;  // Importing JUnit Assertions for validating results
import org.junit.jupiter.api.Test;  // Importing JUnit Test annotation for test methods

/**
 * Test class for the ClassDiagram class.
 * Contains a unit test to verify the functionality of the display method in the ClassDiagram class.
 */
class ClassDiagramTest {

    /**
     * Tests the display method of the ClassDiagram class.
     * Verifies that the display method can be called without throwing any exceptions.
     */
    @Test
    void testDisplay() {
        // Create an instance of the ClassDiagram class
        ClassDiagram classDiagram = new ClassDiagram();
        // Verify that calling the display method does not throw any exceptions
        Assertions.assertDoesNotThrow(classDiagram::display,
                "The display method should not throw an exception.");
    }
}
