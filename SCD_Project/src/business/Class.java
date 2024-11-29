/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package business;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
// Class Class implementing Component
public class Class implements Component {
    private String name;
    private List<String> attributes;
    private List<String> methods;
    private int noOfPartitions;
    private Point position;

    public Class(String name, int noOfPartitions, boolean drawPartition, Point position) {
        this.name = name;
        this.attributes = new ArrayList<>();
        this.methods = new ArrayList<>();
        this.noOfPartitions = noOfPartitions;
        this.position = position;
    }

    public Class() {
        name="Class";
        attributes=new ArrayList<>();
        methods=new ArrayList<>();
        noOfPartitions=0;
        position=new Point(100,100);
    }

    public String getDiagramNotes(){
        //in the format
        //<Class Name>
        //-- (if there are attributes OR methods)
        //attributes (if there are any)
        //-- (if there are methods)
        //methods
        String notes="";
        notes+=name+"\n";
        boolean methodWritten=false;
        for(int i=0;i<noOfPartitions;i++){
            notes += "--\n";
            if(i==0 && !attributes.isEmpty()) {
                for(int j=0;j<attributes.size();j++) {
                    notes +=attributes.get(j)+"\n";
                }
            }
            else if(i==0 && !methods.isEmpty()) {
                for(int j=0;j<methods.size();j++) {
                    notes +=methods.get(j)+"\n";
                }
                methodWritten=true;
            }
            else if(i==1 && !methods.isEmpty() && methodWritten) {
                for(int j=0;j<methods.size();j++) {
                    notes +=methods.get(j)+"\n";
                }
            }
        }
        return notes;
    }
    public void addAttribute(String att){
        attributes.add(att);
    }
    public void addMethod(String m){
        methods.add(m);
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

    public int getNoOfPartitions() {
        return noOfPartitions;
    }

    public void setNoOfPartitions(int noOfPartitions) {
        this.noOfPartitions = noOfPartitions;
    }
    public void add() {
        // Implementation to be added
    }

    public void remove() {
        // Implementation to be added
    }

    public String get() {
        // Implementation to be added
        return null;
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
}
