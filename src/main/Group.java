package main;

import java.util.LinkedList;

public class Group {
    private int id;
    private LinkedList<Integer> editIds = new LinkedList<>();
    public Group(){
        id = 0;
    }
    public Group(int id){
        this.id = id;
    }
    
    //New constructor to create group with data
    public Group(int id, int[] editIds) {
    	this.id = id;
    	this.editIds = new LinkedList<Integer>();
    	for (int i = 0; i != editIds.length; i++) {
    	    this.editIds.add(editIds[i]);
        }
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    public LinkedList<Integer> edits() {
    	return editIds;
    }
    
    public int id(){
        return id;
    }

    public void addEdits(int[] editIds){
        for (int i = 0; i != editIds.length; i++) {
            this.editIds.add(editIds[i]);
        }
    }

    public void removeEdits(int[] editIds){
        for (int i = 0; i != editIds.length; i++) {
            this.editIds.remove(editIds[i]);
        }
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