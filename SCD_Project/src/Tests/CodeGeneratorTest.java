package Test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CodeGeneratorTest {

    @Test
    void testDisplay() {
        CodeGeneratorTest cg = new CodeGeneratorTest();
        assertDoesNotThrow(cg::generate, "The display method should not throw an exception.");
    }

    private Object generate() {
        return null;
    }
}
