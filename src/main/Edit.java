package main;

public class Edit {
    private int id;
    private static int masterId = 0;
    private boolean isUndone = false;
    private EditType type; //insert or delete
    private int[] pieces;

    public Edit(EditType type, int[] pieceIds){
        this.id = masterId++;
        this.type = type;
        this.pieces = pieceIds;
    }

    public int getId(){
        return id;
    }
}

enum EditType {
    INSERT,
    DELETE
}