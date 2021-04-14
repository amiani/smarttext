package main;

public class Piece {
    private int id;
    protected static int masterId = 0;
    private String text;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public void toggleVisible() {
        isVisible = !isVisible;
    }

    private boolean isVisible;

    public Piece(String text, Piece parent) {
        this.text = text;
        this.id = parent.id();
        this.isVisible = parent.isVisible();
    }

    public Piece(String text){
        this.id = masterId++;
        this.isVisible = true;
        this.text = text;
    }

    public Piece(Piece that) {
        this.text = that.text;
        this.id = that.id;
        this.isVisible = that.isVisible;
    }

    public int id(){
        return id;
    }
    public String text() { return text; }
    public int length() { return text.length(); }
}
