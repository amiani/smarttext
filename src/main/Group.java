package main;

import java.util.LinkedList;

public class Group {
    private int id;
    private String name;
    private LinkedList<Edit> edits;
    
    public Group(){
        id = 0;
        name = "All Edits";
        edits = new LinkedList<Edit>();
    }
    
    public Group(int id){
        this.id = id;
        if (id == 0)
        	name = "All Edits";
        else
        	name = "Default " + id;
        edits = new LinkedList<Edit>();
    }
    
    public Group(int id, String name){
        this.id = id;
        this.name = name;
        edits = new LinkedList<Edit>();
    }
    
    public LinkedList<Edit> getEdits() {
    	return edits;
    }
    
    @Override
    public String toString() {
    	return name;
    }
    
    public int getId(){
        return id;
    }
    
    public void addEdits(int[] editIds){

        return;
    }
    
    public void removeEdits(int[] editIds){

        return;
    }
}
