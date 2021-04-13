package main;

import org.junit.jupiter.api.Test;

import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentEditManagerSyncTest {
    @Test
    public void testInsert() {
        PlainDocument doc = new PlainDocument();
        EditManager em = new EditManager();
        EditListener el = new EditListener(em);
        doc.addDocumentListener(el);

        try {
            doc.insertString(0, "abcd", null);
            assertEquals(getAllText(doc), em.getText());

            doc.insertString(4, "efgh", null);
            assertEquals(getAllText(doc), em.getText());

            doc.insertString(2, "AB", null);
            assertEquals(getAllText(doc), em.getText());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDelete() {
        PlainDocument doc = new PlainDocument();
        EditManager em = new EditManager();
        EditListener el = new EditListener(em);
        doc.addDocumentListener(el);

        try {
            doc.insertString(0, "abcdefgh", null);
            doc.remove(0, 2);
            assertEquals(getAllText(doc), em.getText());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private String getAllText(PlainDocument d) {
        try {
            return d.getText(0, d.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return "";
    }
}
