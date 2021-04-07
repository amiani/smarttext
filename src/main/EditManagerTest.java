package main;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditManagerTest {
    @Test
    public void testFindPiece() {
        EditManager em = new EditManager();
        Piece p1 = new Piece("hello");
        Piece p2 = new Piece("my");
        Piece p3 = new Piece("name");
        LinkedList<Piece> pieces = new LinkedList<>(Arrays.asList(
                p1, p2, p3));
        assertEquals(p1, em.findPiece(pieces, 0));
        assertEquals(p2, em.findPiece(pieces, 5));
        assertEquals(p3, em.findPiece(pieces, 9));
    }

    @Test
    public void testSplitPiece() {
        EditManager em = new EditManager();
        Piece p1 = new Piece("hello");
        Piece p3 = new Piece("name");
        assertEquals(2, em.splitPiece(p1, 2, 0).length);
        assertEquals("n", em.splitPiece(p3, 1, 0)[0].text());
        assertEquals("ame", em.splitPiece(p3, 1, 0)[1].text());
        assertEquals("e", em.splitPiece(p3, 1, 2)[1].text());
        assertEquals(1, em.splitPiece(p1, 4, 1).length);
    }
}
