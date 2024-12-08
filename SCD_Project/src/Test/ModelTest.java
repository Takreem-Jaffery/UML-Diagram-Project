package Test;

import business.Model;  // Import the Model class from the business package
import org.junit.jupiter.api.Assertions;  // Import JUnit's Assertions class for writing assertions
import org.junit.jupiter.api.Test;  // Import JUnit's Test annotation to mark test methods

// Test class for the Model class
class ModelTest {

    // Test for the 'add' method in the Model class
    @Test
    void testAddMethod() {
        Model model = new Model();  // Create a new instance of the Model class

        // Assert that calling the 'add' method does not throw any exceptions
        Assertions.assertDoesNotThrow(model::add, "The add method should not throw an exception.");

        // Additional checks can be added here to verify that 'add' works as expected
        // Example: Verify that the model now contains the expected object, if relevant
    }

    // Test for the 'remove' method in the Model class
    @Test
    void testRemoveMethod() {
        Model model = new Model();  // Create a new instance of the Model class

        // Assert that calling the 'remove' method does not throw any exceptions
        Assertions.assertDoesNotThrow(model::remove, "The remove method should not throw an exception.");

        // Additional checks can be added here to verify the behavior after removal
        // Example: Ensure that the model is empty or contains the correct items after removal
    }
}
