package business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.*;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UseCaseArrow implements Component {
    String name;
    String type;
    Point startPoint;
    Point endPoint;

    public UseCaseArrow(String name, String type, Point startPoint, Point endPoint) {
        this.name = name;
        this.type = type;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public UseCaseArrow() {
        name = "";
        type = "";
        startPoint = null;
        endPoint = null;
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
        return "Arrow";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }
}
