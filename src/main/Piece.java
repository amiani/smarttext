package main;

public class Piece {
    private int id;
    private static int masterId = 0;
    private String text;
    private boolean isVisible;

    public Piece(String text){
        this.id = masterId++;
        this.isVisible = true;
        this.text = text;
    }

    public int getId(){
        return id;
    }
    public String getText() { return text; }
}
