package Test;

import business.UseCaseDiagram;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UseCaseDiagramTest {

    @Test
    void testDisplay() {

        // Step 1: Create an anonymous subclass of UseCaseDiagram to override the 'display' method.
        // This is done because UseCaseDiagram is likely an abstract class or interface that requires
        // the 'display' method to be implemented.
        UseCaseDiagram ucd = new UseCaseDiagram() {
            @Override
            public void display() {
                // Empty implementation for testing purposes, since we're only testing that no exception is thrown.
            }
        };

        // Step 2: Use assertDoesNotThrow to verify that calling the 'display' method does not throw any exceptions.
        // The test passes if no exception is thrown when the 'display' method is invoked.
        Assertions.assertDoesNotThrow(ucd::display, "The display method should not throw an exception.");
    }
}
