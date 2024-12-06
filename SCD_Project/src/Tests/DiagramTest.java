package Test;

import business.Diagram;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DiagramTest {

    @Test
    void testDisplay() {
        Diagram d = new Diagram() {
            @Override
            public void display() {
            }
        };
        Assertions.assertDoesNotThrow(d::display, "The display method should not throw an exception.");
    }
}
