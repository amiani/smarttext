package main;

import javax.swing.JList;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class EditListener implements DocumentListener {
    EditManager editManager;
    GroupManager gm;
    
    JList editlist;
    public EditListener(EditManager em, GroupManager gm, JList editlist) {
        this.editManager = em;
        this.gm = gm;
        this.editlist  = editlist;
    }

    private EditType lastEdit;

    public void insertUpdate(DocumentEvent e) {
        Document doc = e.getDocument();
        try {
        	if (Listener.getActive()) {
                int position = e.getOffset();
                String text = doc.getText(position, e.getLength());
                System.out.println(editManager.getText());
            	editManager.insert(position, text);
                //System.out.println(position + ": " + text);
            	editlist.setListData(Listener.getDefaultList().toArray());
        	}
        } catch (BadLocationException badLocationException) {
            badLocationException.printStackTrace();
        }
    }

    public void removeUpdate(DocumentEvent e) {
    	if (Listener.getActive()) {
            Document doc = e.getDocument();
            editManager.delete(e.getOffset(), e.getLength());
    	}
    }

    public void changedUpdate(DocumentEvent e) {}
}