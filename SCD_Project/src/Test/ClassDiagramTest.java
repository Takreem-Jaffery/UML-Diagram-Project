package Test;

import business.ClassDiagram;  // Importing the ClassDiagram class to be tested
import org.junit.jupiter.api.Assertions;  // Importing JUnit Assertions for validating results
import org.junit.jupiter.api.Test;  // Importing JUnit Test annotation for test methods

// Test class for verifying the functionality of the ClassDiagram class
class ClassDiagramTest {

    // Test method for verifying the display functionality of the ClassDiagram class
    @Test
    void testDisplay() {
        // Create an instance of the ClassDiagram class
        ClassDiagram classDiagram = new ClassDiagram();
        // Verify that calling the display method does not throw any exceptions
        Assertions.assertDoesNotThrow(classDiagram::display, "The display method should not throw an exception.");
        // The message provided will be shown if the display method throws an exception
    }
}
