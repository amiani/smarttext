package main;

public class Edit {
    private int id;
    protected static int masterId = 0;
    private boolean isUndone = false;
    private EditType type; //insert or delete
    private int pieceId;

    public Edit(EditType type) {
        this.id = masterId++;
        this.type = type;
        this.pieceId = this.id;
    }

    public String toString() {
        return "Edit " + id + " "
                + (isUndone ? "x" : "✓");
    }
    public int id(){
        return id;
    }
    public int pieceId() { return pieceId;}
}

enum EditType {
    INSERT,
    DELETE
}