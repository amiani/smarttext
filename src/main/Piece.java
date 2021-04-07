package main;

public class Piece {
    private int id;
    private static int masterId = 0;
    private String text;

    public boolean isVisible() {
        return isVisible;
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

    public int id(){
        return id;
    }
    public String text() { return text; }
    public int length() { return text.length(); }
}
