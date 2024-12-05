package Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UseCaseDiagramTest {

    @Test
    void testDisplay() {
        UseCaseDiagram ucd = new UseCaseDiagram() {
            @Override
            public void display() {
            }
        };
        Assertions.assertDoesNotThrow(ucd::display, "The display method should not throw an exception.");
    }
}
