/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.awt.*;

/**
 *
 * @author hp
 */
// Usecase Class implementing Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usecase implements Component {
    private String name;
    private Point position;

    public Usecase(String name, Point position) {
        this.name = name;
        this.position = position;
    }

    public Usecase() {
        name="Usecase";
        position=null;
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
    public String getDiagramNotes(){
        return name+"\n";
    }
    @Override
    public void display() {
        // Implementation to be added
    }

    @Override
    public void addProperty() {
        // Implementation to be added
    }

    @Override
    public void removeProperty() {
        // Implementation to be added
    }

    @Override
    public void addConstraint() {
        // Implementation to be added
    }

    @Override
    public void removeConstraint() {
        // Implementation to be added
    }
    @Override
    public String getClassType(){
        return "UseCase";
    }
}

