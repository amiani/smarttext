package main;

public class Edit {
    private int id;
    protected static int masterId = 0;
    private boolean isUndone = false;
    private EditType type; //insert or delete
    private int[] pieces;

    public Edit(EditType type, int[] pieceIds){
        this.id = masterId++;
        this.type = type;
        this.pieces = pieceIds;
    }
    public String toString() {
        return "Edit #".concat(String.valueOf(id));
    }

    public int id(){
        return id;
    }
    public int[] pieces() { return pieces;}
}

enum EditType {
    INSERT,
    DELETE
}