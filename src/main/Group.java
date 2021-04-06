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
