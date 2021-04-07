package main;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EditListener implements DocumentListener {
    EditManager editManager;
    public EditListener(EditManager em) {
        this.editManager = em;
    }

    public void insertUpdate(DocumentEvent e) {

    }

    public void removeUpdate(DocumentEvent e) {

    }

    public void changedUpdate(DocumentEvent e) {}
}
