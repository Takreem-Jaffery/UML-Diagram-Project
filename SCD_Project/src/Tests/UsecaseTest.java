package Test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

class UsecaseTest {

    @Test
    void testUsecase() {

        String initialName = "TestUsecase";
        Point initialPosition = new Point(50, 100);
        Usecase usecase = new Usecase(initialName, initialPosition);

        String name = usecase.getName();
        Point position = usecase.getPosition();
        String diagramNotes = usecase.getDiagramNotes();

        assertEquals(initialName, name, "The name should be the same as the initial name.");
        assertEquals(initialPosition, position, "The position should be the same as the initial position.");
        assertEquals(initialName + "\n", diagramNotes, "The diagram notes should contain the name of the usecase.");

        String newName = "UpdatedUsecase";
        Point newPosition = new Point(150, 200);
        usecase.setName(newName);
        usecase.setPosition(newPosition);

        Assertions.assertEquals(newName, usecase.getName(), "The name should be updated.");
        Assertions.assertEquals(newPosition, usecase.getPosition(), "The position should be updated.");

        Assertions.assertEquals(newName + "\n", usecase.getDiagramNotes(), "The diagram notes should reflect the updated name.");
    }
}
