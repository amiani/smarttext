package main;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentEditManagerSyncTest {
    @Test
    public void testInsert() {
        PlainDocument doc = new PlainDocument();
        EditManager em = new EditManager();
        GroupManager gm = new GroupManager();
        EditListener el = new EditListener(em, gm, new JList());
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
        GroupManager gm = new GroupManager();
        EditListener el = new EditListener(em, gm, new JList());
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
        GroupManager gm = new GroupManager();
        EditListener el = new EditListener(em, gm, new JList());
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
            doc.insertString(5, "XY", null);    //4
            assertEquals("ABijkXYl", em.getText());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUndoInsertDelete() {
        PlainDocument doc = new PlainDocument();
        EditManager em = new EditManager();
        GroupManager gm = new GroupManager();
        EditListener el = new EditListener(em, gm, new JList());
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
            doc.insertString(5, "XY", null);    //4
            assertEquals("ABijkXYl", em.getText());
            doc.remove(6, 4);                       //5
            assertEquals("ABijkX", em.getText());
            doc.remove(2, 5);
            assertEquals("AB", em.getText());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUndoToggle() {
        PlainDocument doc = new PlainDocument();
        EditManager em = new EditManager();
        GroupManager gm = new GroupManager();
        EditListener el = new EditListener(em, gm, new JList());
        doc.addDocumentListener(el);
        Edit.masterId = 0;

        try {
            doc.insertString(0, "abcd", null);  //0
            doc.remove(3, 1);
            em.undo(new int[]{1});
            assertEquals("abcd", em.getText());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsertDeleteUndoFirst() {
        PlainDocument doc = new PlainDocument();
        EditManager em = new EditManager();
        GroupManager gm = new GroupManager();
        EditListener el = new EditListener(em, gm, new JList());
        doc.addDocumentListener(el);
        Edit.masterId = 0;

        try {
            doc.insertString(0, "abcd", null);  //0
            doc.remove(3, 1);
            em.undo(new int[]{0});
            assertEquals("", em.getText());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSingleInsert() {
        PlainDocument doc = new PlainDocument();
        EditManager em = new EditManager();
        GroupManager gm = new GroupManager();
        EditListener el = new EditListener(em, gm, new JList());
        doc.addDocumentListener(el);
        Edit.masterId = 0;

        try {
            doc.insertString(0, "a", null);  //0
            doc.insertString(1, "b", null);  //0
            doc.insertString(2, "c", null);  //0
            doc.insertString(3, "d", null);  //0
            assertEquals("abcd", em.getText());
            doc.remove(1, 2);
            assertEquals("ad", em.getText());
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
