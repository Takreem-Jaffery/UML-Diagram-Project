package business;

/**
 * The Diagram interface represents a generic diagram in the system.
 * It serves as a base for different diagram types, such as UML diagrams, use case diagrams, etc.
 * The interface defines a method for displaying the diagram.
 */
public interface Diagram {

    /**
     * Displays the diagram. The exact implementation of the display may vary depending on the specific diagram type.
     */
    void display();
}
