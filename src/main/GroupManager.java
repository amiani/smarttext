package main;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.LinkedList;

public class GroupManager implements DocumentListener{
    private LinkedList<Group> groups;
    public GroupManager(){
        groups = new LinkedList<Group>();
    }
    public void insertUpdate(DocumentEvent e) {

    }

    public void removeUpdate(DocumentEvent e) {

    }

    public void changedUpdate(DocumentEvent e) {}
}
