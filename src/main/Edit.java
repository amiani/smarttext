package main;

public class Edit {
    private int id;
    protected static int masterId = 0;

    private boolean isUndone = false;
    public boolean isUndone() {
        return isUndone;
    }
    public void undo() {
        isUndone = true;
    }

    private EditType type; //insert or delete
    public EditType type() {
        return type;
    }

    private int pieceId;
    public int id(){
        return id;
    }
    public int pieceId() { return id;}

    public Edit(EditType type) {
        this.id = masterId++;
        this.type = type;
        this.pieceId = this.id;
    }

    public String toString() {
        return "Edit " + id + " "
                + (isUndone ? "x" : "✓");
    }
}

enum EditType {
    INSERT,
    DELETE
}