package business;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

// Component Interface
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
    void display();
    void addProperty();
    void removeProperty();
    void addConstraint();
    void removeConstraint();
    String getClassType();

}