package business;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;

public class Comment implements Component{
    String commentText;
    Point position;

    public Comment(String text, Point p){
        commentText=text;
        position=p;
    }

    public Comment() {
        commentText="";
        position=null;
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
