package business;

import java.awt.Point;

public class UCDSystem implements Component {
    String name;
    Point position;
    int width;
    int height;

    public UCDSystem(String name, Point p, int w,int h){
        this.name=name;
        position=p;
        width=w;
        height=h;
    }
    public UCDSystem(){
        name="System";
        position=null;
        width=150;
        height=100;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
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
        return "System";
    }
}
