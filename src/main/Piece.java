package main;

import java.util.ArrayList;

public class Piece {
    private ArrayList<Integer> editIds = new ArrayList<>();
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
        this.editIds = parent.ids();
        this.isVisible = parent.isVisible();
    }

    public Piece(String text){
        this.editIds.add(masterId++);
        this.isVisible = true;
        this.text = text;
    }

    public Piece(Piece that) {
        this.text = that.text;
        this.editIds = that.editIds;
        this.isVisible = that.isVisible;
    }

    public Piece(String text, int editId) {
        this.text = text;
        this.editIds.add(editId);
        this.isVisible = true;
    }

    public ArrayList<Integer> ids() { return editIds; }
    public void addId(int editId) { editIds.add(editId); }
    public String text() { return text; }
    public int length() { return text.length(); }
}