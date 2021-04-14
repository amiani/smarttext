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

            doc.remove(2, 2);
            assertEquals(getAllText(doc), em.getText());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUndoInsert() {
        PlainDocument doc = new PlainDocument();
        EditManager em = new EditManager();
        EditListener el = new EditListener(em);
        doc.addDocumentListener(el);

        try {
            doc.insertString(0, "abcd", null);  //0
            doc.insertString(4, "efgh", null);  //1
            em.undo(new int[]{0});
            assertEquals("efgh", em.getText());
            doc.insertString(4, "ijkl", null);  //2
            assertEquals("efghijkl", em.getText());
            doc.insertString(2, "AB", null);    //3
            em.undo(new int[]{1});
            assertEquals("ABijkl", em.getText());
            em.redo(new int[]{0});
            assertEquals("abcdABijkl", em.getText());
            doc.insertString(5, "XY", null);    //4
            assertEquals("abcdAXYBijkl", em.getText());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUndoInsertDelete() {
        PlainDocument doc = new PlainDocument();
        EditManager em = new EditManager();
        EditListener el = new EditListener(em);
        doc.addDocumentListener(el);
        Edit.masterId = 0;

        try {
            doc.insertString(0, "abcd", null);  //0
            doc.insertString(4, "efgh", null);  //1
            em.undo(new int[]{0});
            assertEquals("efgh", em.getText());
            doc.insertString(4, "ijkl", null);  //2
            assertEquals("efghijkl", em.getText());
            doc.insertString(2, "AB", null);    //3
            em.undo(new int[]{1});
            assertEquals("ABijkl", em.getText());
            em.redo(new int[]{0});
            assertEquals("abcdABijkl", em.getText());
            doc.insertString(5, "XY", null);    //4
            assertEquals("abcdAXYBijkl", em.getText());
            doc.remove(6, 4);                       //5
            assertEquals("abcdAXkl", em.getText());
            doc.remove(2, 5);
            assertEquals("abl", em.getText());
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
