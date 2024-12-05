package business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.awt.*;
import java.util.ArrayList;

// Class Class implementing Component
//it will ignore unrecognized fields like "diagramNotes"
@JsonIgnoreProperties(ignoreUnknown = true)
public class Class implements Component {
    private String name;
    private int noOfPartitions;
    @JsonProperty("attributes")
    private ArrayList<String> attributes;
    @JsonProperty("methods")
    private ArrayList<String> methods;
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

    public ArrayList<String> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<String> attributes) {
        this.attributes = attributes;
    }

    public ArrayList<String> getMethods() {
        return methods;
    }

    public void setMethods(ArrayList<String> methods) {
        this.methods = methods;
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
        return "Class";
    }
}
