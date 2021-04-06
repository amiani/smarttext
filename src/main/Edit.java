package main;

public class Edit {
    private int id;
    private boolean isUndone = false;
    private String type; //insert or delete
    private int[] pieces;

    public Edit(int id){
    this.id = id;
    }
    public Edit(int id, int[] pieceIds){
        this.id = id;
        this.pieces = pieceIds;
    }
    public int getId(){
        return id;
    }
}
