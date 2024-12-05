package Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClassDiagramTest {

    @Test
    void testDisplay() {
        ClassDiagram classDiagram = new ClassDiagram();
        Assertions.assertDoesNotThrow(classDiagram::display, "The display method should not throw an exception.");
    }
}
