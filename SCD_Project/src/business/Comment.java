package business;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;

public class Comment implements Component{
    String name;
    String commentText;
    Point position;

    public Comment(String name, String text, Point p){
        this.name=name;
        commentText=text;
        position=p;
    }
    public Comment(String text, Point p){
        this.name="Comment...";
        commentText=text;
        position=p;
    }
    public Comment() {
        name="Comment...";
        commentText="";
        position=null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
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

    //json is having a hard time recognizing this for comment class
    @JsonProperty("classType")
    @Override
    public String getClassType(){
        return "Comment";
    }
}
