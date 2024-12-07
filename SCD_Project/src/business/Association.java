package business;

import java.awt.Point;

public class Association implements Component{
    String type;
    String name;
    Point startPoint;
    Point endPoint;

    public Association(){
        type="";
        name="";
        startPoint=null;
        endPoint=null;
    }

    public Association(String type, String name, Point startPoint, Point endPoint) {
        this.type = type;
        this.name = name;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
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

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
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
    public String getClassType(){
        return "Association";
    }
}
