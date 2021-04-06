package main;

public class Piece {
    private int id;
    private char text;
    private boolean isVisible;

    public Piece(int id, char text){
        this.id = id;
        this.isVisible = true;
        this.text = text;
    }
    public int getId(){
        return id;
    }
}
