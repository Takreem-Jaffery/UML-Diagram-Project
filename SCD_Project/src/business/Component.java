package business;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The Component interface represents a generic component that can be part of a UML or use case diagram.
 * It is the base interface for different types of diagram elements such as classes, associations, comments, etc.
 * The interface defines methods for displaying, adding/removing properties and constraints, and identifying the component type.
 * This interface uses Jackson annotations for polymorphic serialization.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "classType" // JSON property to distinguish types
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Class.class, name = "Class"),
        @JsonSubTypes.Type(value = Association.class, name = "Association"),
        @JsonSubTypes.Type(value = Comment.class, name = "Comment"),
        @JsonSubTypes.Type(value = Usecase.class, name="UseCase"),
        @JsonSubTypes.Type(value = Actor.class, name="Actor"),
        @JsonSubTypes.Type(value = UseCaseArrow.class, name="Arrow"),
        @JsonSubTypes.Type(value = UCDSystem.class, name="System")
})
public interface Component {

    /**
     * Displays the component in a diagram or graphical representation.
     */
    void display();

    /**
     * Adds a property to the component. The specific property depends on the component type.
     */
    void addProperty();

    /**
     * Removes a property from the component. The specific property depends on the component type.
     */
    void removeProperty();

    /**
     * Adds a constraint to the component. Constraints define specific conditions or limitations for the component.
     */
    void addConstraint();

    /**
     * Removes a constraint from the component.
     */
    void removeConstraint();

    /**
     * Gets the class type of the component, which helps to identify the type of diagram element (e.g., Class, Association).
     * @return The class type of the component.
     */
    String getClassType();
}
