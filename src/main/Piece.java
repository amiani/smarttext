package main;

import java.util.ArrayList;

public class Piece {
    private ArrayList<Integer> ids = new ArrayList<>();
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
        this.ids = parent.ids();
        this.isVisible = parent.isVisible();
    }

    public Piece(String text){
        this.ids.add(masterId++);
        this.isVisible = true;
        this.text = text;
    }

    public Piece(Piece that) {
        this.text = that.text;
        this.ids = that.ids;
        this.isVisible = that.isVisible;
    }

    public Piece(String text, int editId) {
        this.text = text;
        this.ids.add(editId);
        this.isVisible = true;
    }

    public ArrayList<Integer> ids() { return ids; }
    public void addId(int editId) { ids.add(editId); }
    public String text() { return text; }
    public int length() { return text.length(); }
}