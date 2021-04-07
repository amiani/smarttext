package main;

public class Piece {
    private int id;
    private static int masterId = 0;
    private char text;
    private boolean isVisible;

    public Piece(char text){
        this.id = masterId++;
        this.isVisible = true;
        this.text = text;
    }

    public int getId(){
        return id;
    }
}
