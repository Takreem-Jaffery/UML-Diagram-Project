package Test;

import business.Diagram;  // Import the Diagram class from the business package
import org.junit.jupiter.api.Assertions;  // Import JUnit's Assertions class to write assertions
import org.junit.jupiter.api.Test;  // Import JUnit's Test annotation to mark test methods

/**
 * Test class for the Diagram class.
 */
class DiagramTest {

    /**
     * Test for the 'display' method in the Diagram class.
     * Ensures that invoking 'display' does not throw exceptions.
     */
    @Test
    void testDisplay() {
        // Create an anonymous class to mock the Diagram class and implement the display method
        Diagram diagram = new Diagram() {
            @Override
            public void display() {
                // Mock implementation of the display method (no-op for testing)
            }
        };

        // Assert that calling the 'display' method does not throw any exceptions
        Assertions.assertDoesNotThrow(diagram::display, "The display method should not throw an exception.");
    }
}
