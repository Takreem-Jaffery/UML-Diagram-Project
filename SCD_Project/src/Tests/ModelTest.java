package Test;

import business.Model;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ModelTest {

    @Test
    void testAddMethod() {
        Model model = new Model();
        Assertions.assertDoesNotThrow(model::add, "The add method should not throw an exception.");
    }

    @Test
    void testRemoveMethod() {
        Model model = new Model();
        Assertions.assertDoesNotThrow(model::remove, "The remove method should not throw an exception.");
    }
}
