package main;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

        assertEquals(null, em.findPiece(pieces, 0).piece);
        assertEquals(p1, em.findPiece(pieces, 1).piece);
        assertEquals(null, em.findPiece(pieces, 5).piece);
        assertEquals(p2, em.findPiece(pieces, 6).piece);
        assertEquals(p3, em.findPiece(pieces, 9).piece);
        assertEquals(2, em.findPiece(pieces, 2).piecePosition);
        assertEquals(1, em.findPiece(pieces, 6).piecePosition);
        assertEquals(null, em.findPiece(pieces, 11).piece);
    }

    @Test
    public void testSplitPiece() {
        EditManager em = new EditManager();
        Piece p1 = new Piece("hello");
        Piece p3 = new Piece("name");
        assertEquals(2, em.splitPiece(p1, 2, 0).length);
        assertEquals("n", em.splitPiece(p3, 1, 0)[0].text());
        assertEquals("ame", em.splitPiece(p3, 1, 0)[1].text());
        assertEquals("am", em.splitPiece(p3, 1, 2)[1].text());
        assertEquals(2, em.splitPiece(p1, 4, 1).length);
        assertEquals(3, em.splitPiece(p1, 2, 1).length);
    }

    @Test
    public void testInsertPiece() {
        EditManager em = new EditManager();
        Piece p1 = new Piece("hello");
        Piece p2 = new Piece("my");
        Piece p3 = new Piece("name");
        LinkedList<Piece> pieces = new LinkedList<>(Arrays.asList(new Piece("")));
        ArrayList<Piece> pieceList = new ArrayList<>();
        pieceList.add(p1);
        assertEquals(p1.text(), em.insertPiece(pieces, 0, pieceList).get(0).text());

        pieces = new LinkedList<>(Arrays.asList(p1, p2, p3));
        Piece pa = new Piece("a");
        Piece pb = new Piece("b");
        Piece pc = new Piece("c");
        Piece pnew = new Piece("new");
        ArrayList<Piece> splits = new ArrayList<>();
        splits.add(pa);
        splits.add(pnew);
        splits.add(pb);
        splits.add(pc);
        assertEquals(pa.text(), em.insertPiece(pieces, 0, splits).get(0).text());
        assertEquals(pnew.text(), em.insertPiece(pieces, 0, splits).get(1).text());
        assertEquals(p2.text(), em.insertPiece(pieces, 3, splits).get(1).text());
        assertEquals(pb.text(), em.insertPiece(pieces, 2, splits).get(4).text());

    }
}
