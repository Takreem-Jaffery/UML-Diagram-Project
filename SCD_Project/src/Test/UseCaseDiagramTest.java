package Test;

import business.UseCaseDiagram;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test class for the UseCaseDiagram class.
 * This class contains tests for verifying the functionality of the 'display' method in the UseCaseDiagram.
 */
class UseCaseDiagramTest {

    /**
     * Test method for verifying that the 'display' method in UseCaseDiagram does not throw any exceptions.
     * An anonymous subclass of UseCaseDiagram is created to implement the abstract 'display' method.
     */
    @Test
    void testDisplay() {
        UseCaseDiagram ucd = new UseCaseDiagram() {
            @Override
            public void display() {
                // Empty implementation for testing purposes
            }
        };

        Assertions.assertDoesNotThrow(ucd::display, "The display method should not throw an exception.");
    }
}
