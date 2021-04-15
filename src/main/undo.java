package main;

public interface undo {
    public void undo(int[] editIds);

    public void redo(int[] editIds);
}
