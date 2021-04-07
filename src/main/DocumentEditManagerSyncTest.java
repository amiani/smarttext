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
            assertEquals(doc.getText(0, doc.getLength()), em.getText());
            doc.insertString(4, "efgh", null);
            assertEquals(doc.getText(0, doc.getLength()), em.getText());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
