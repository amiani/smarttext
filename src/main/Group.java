package main;

import java.util.LinkedList;

public class Group {
    private int id;
    private LinkedList<Edit> edits;
    public Group(){
        id = 0;
        edits = new LinkedList<Edit>();
    }
    public Group(int id){
        this.id = id;
        edits = new LinkedList<Edit>();
    }
    
    //New constructor to create group with data
    public Group(int id, LinkedList<Edit> edits) {
    	this.id = id;
    	this.edits = edits;
    }
    
    public LinkedList<Edit> getEdits(){
    	return edits;
    }
    
    public int getId(){
        return id;
    }
    public void addEdits(int[] editIds){
    	//Listener.getActiveList().add
        return;
    }
    public void removeEdits(int[] editIds){

        return;
    }
    public String toString() {
    	if(id == 0) {
    		return "Default Group";
    	}
    	else {
    	return "Group #" + id;
    	}
    }
}
