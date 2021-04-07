package main;

public class Edit {
    private int id;
    private static int masterId = 0;
    private boolean isUndone = false;
    private String type; //insert or delete
    private int[] pieces;

    public Edit(int[] pieceIds){
        this.id = masterId++;
        this.pieces = pieceIds;
    }

    public int getId(){
        return id;
    }
}
