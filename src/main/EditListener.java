package main;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class EditListener implements DocumentListener {
    EditManager editManager;
    public EditListener(EditManager em) {
        this.editManager = em;
    }

    public void insertUpdate(DocumentEvent e) {
        Document doc = e.getDocument();
        try {
            int position = e.getOffset();
            String text = doc.getText(position, e.getLength());
            editManager.insertString(position, text);
            System.out.println(position + ": " + text);
        } catch (BadLocationException badLocationException) {
            badLocationException.printStackTrace();
        }
    }

    public void removeUpdate(DocumentEvent e) {

    }

    public void changedUpdate(DocumentEvent e) {}
}
