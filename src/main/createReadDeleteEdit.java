package main;

import java.util.LinkedList;

public interface createReadDeleteEdit {
    public void insert(int position, char c);
    public void delete(int position);
    public LinkedList<Edit> getEdits();
    public void deleteEdits(int[] editIds);
}
