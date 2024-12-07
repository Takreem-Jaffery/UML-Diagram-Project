package business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Actor implements Component{
    String name;
    java.awt.Point position;

    public Actor(String name, java.awt.Point position) {
        this.name = name;
        this.position = position;
    }

    public Actor() {
        name="Actor";
        position=null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.awt.Point getPosition() {
        return position;
    }

    public void setPosition(java.awt.Point position) {
        this.position = position;
    }

    @Override
    public void display() {

    }

    @Override
    public void addProperty() {

    }

    @Override
    public void removeProperty() {

    }

    @Override
    public void addConstraint() {

    }

    @Override
    public void removeConstraint() {

    }

    @Override
    public String getClassType() {
        return "Actor";
    }
}
