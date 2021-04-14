package main;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static main.EditType.INSERT;
import static org.junit.jupiter.api.Assertions.*;

public class EditManagerTest {
    @Test
    public void testFindPiece() {
        EditManager em = new EditManager();
        Piece p1 = new Piece("hello");
        Piece p2 = new Piece("my");
        Piece p3 = new Piece("name");
        LinkedList<Piece> pieces = new LinkedList<>(Arrays.asList(
                p1, p2, p3));
        ArrayList<FindResult> expected = new ArrayList<>();

        expected.add(new FindResult(p1, 0, 0));
        assertTrue(compareFindResult(expected, em, pieces, 0, 0));

        expected.clear();
        expected.add(new FindResult(p1, 0, 1));
        assertTrue(compareFindResult(expected, em, pieces, 1, 0));

        expected.clear();
        expected.add(new FindResult(p2, 1, 0));
        assertTrue(compareFindResult(expected, em, pieces, 5, 0));

        expected.clear();
        expected.add(new FindResult(p3, 2, 4));
        assertTrue(compareFindResult(expected, em, pieces, 11, 0));
    }

    private boolean compareFindResult(ArrayList<FindResult> expected,
                                      EditManager em,
                                      List<Piece> pieces,
                                      int position,
                                      int deleteLength) {
        Stream<FindResult> results = em.findPieces(pieces, position, deleteLength);
        ArrayList<FindResult> results2 = (ArrayList<FindResult>) results.collect(Collectors.toList());
        return expected.stream().allMatch(ex -> results2.stream()
                .anyMatch(res -> res.piece == ex.piece
                            && res.piecePosition == ex.piecePosition
                            && res.index == ex.index));
    }

    @Test
    public void testFindPieceInvisible() {
        EditManager em = new EditManager();
        Piece p1 = new Piece("abcd");
        Piece p2 = new Piece("invis1");
        p2.setVisible(false);
        Piece p3 = new Piece("efgh");
        Piece p4 = new Piece("invis2");
        p4.setVisible(false);
        LinkedList<Piece> pieces = new LinkedList<>(Arrays.asList(p1, p2, p3, p4));

        ArrayList<FindResult> expected = new ArrayList<>();
        expected.add(new FindResult(p3, 2, 0));
        assertTrue(compareFindResult(expected, em, pieces, 4, 0));
    }

    @Test
    public void testFindPieceDelete() {
        EditManager em = new EditManager();
        Piece p1 = new Piece("abcd");
        Piece p2 = new Piece("efgh");
        Piece p3 = new Piece("invis1");
        p3.setVisible(false);
        Piece p4 = new Piece("ijkl");
        Piece p5 = new Piece("invis2");
        p5.setVisible(false);
        LinkedList<Piece> pieces = new LinkedList<>(Arrays.asList(p1, p2, p3, p4, p5));

        ArrayList<FindResult> expected = new ArrayList<>();
        expected.add(new FindResult(p1, 0, 0));
        assertTrue(compareFindResult(expected, em, pieces, 0, 2));

        expected.clear();
        expected.add(new FindResult(p1, 0, 2));
        expected.add(new FindResult(p2, 1, 0));
        assertTrue(compareFindResult(expected, em, pieces, 2, 4));
    }

    @Test
    public void testSplitPiece() {
        EditManager em = new EditManager();
        Piece p1 = new Piece("hello");
        Piece p3 = new Piece("name");

        assertEquals("n", em.splitPiece(p3, 1, 0).get(0).text());
        assertEquals("ame", em.splitPiece(p3, 1, 0).get(1).text());
        assertEquals("am", em.splitPiece(p3, 1, 2).get(1).text());
        assertEquals("hello", em.splitPiece(p1, 0, 0).get(0).text());
        assertEquals("hello", em.splitPiece(p1, 5, 0).get(0).text());

        assertEquals(1, em.splitPiece(p1, 0, 0).size());
        assertEquals(1, em.splitPiece(p1, 5, 0).size());
        assertEquals(1, em.splitPiece(p1, 0, 5).size());
        assertEquals(1, em.splitPiece(p1, 0, 5).size());
        assertEquals(2, em.splitPiece(p1, 0, 4).size());
        assertEquals(2, em.splitPiece(p1, 2, 0).size());
        assertEquals(2, em.splitPiece(p1, 4, 1).size());
        assertEquals(3, em.splitPiece(p1, 2, 1).size());

        assertTrue(em.splitPiece(p1, 2, 1).get(0).isVisible());
        assertFalse(em.splitPiece(p1, 2, 1).get(1).isVisible());
        assertTrue(em.splitPiece(p1, 2, 1).get(2).isVisible());
    }

    @Test
    public void testReplacePiece() {
        EditManager em = new EditManager();
        Piece p1 = new Piece("hello");
        Piece p2 = new Piece("my");
        Piece p3 = new Piece("name");
        LinkedList<Piece> pieces = new LinkedList<>();
        pieces.add(new Piece(""));

        ArrayList<Piece> pieceList = new ArrayList<>();
        pieceList.add(p1);
        assertEquals(p1.text(), em.replacePiece(pieces, 0, pieceList).get(0).text());

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
        assertEquals(pa.text(), em.replacePiece(pieces, 0, splits).get(0).text());
        assertEquals(pnew.text(), em.replacePiece(pieces, 0, splits).get(1).text());
        assertEquals(p2.text(), em.replacePiece(pieces, 3, splits).get(1).text());
        assertEquals(pb.text(), em.replacePiece(pieces, 2, splits).get(4).text());

    }

    @Test
    public void testInsertPiece() {
        EditManager em = new EditManager();
        Piece p1 = new Piece("hello");
        Piece p2 = new Piece("my");
        Piece p3 = new Piece("name");
        LinkedList<Piece> pieces = new LinkedList<>(Arrays.asList(p1, p2, p3));

        Piece AB = new Piece("AB");
        assertEquals("AB", em.insertPiece(pieces, 2, AB).get(1).text());
    }

    @Test
    public void testToPieceIds() {
        EditManager em = new EditManager();
        ArrayList<Edit> edits = new ArrayList<>();
        edits.add(new Edit(INSERT, new int[]{0}));
        edits.add(new Edit(INSERT, new int[]{1,2,3}));
        edits.add(new Edit(INSERT, new int[]{1,3,5}));

        assertArrayEquals(new int[]{0}, em.toPieceIds(edits, new int[]{0}));
        assertArrayEquals(new int[]{1,2,3}, em.toPieceIds(edits, new int[]{1}));
        assertArrayEquals(new int[]{1,2,3,5}, em.toPieceIds(edits, new int[]{1, 2}));
    }

    @Test
    public void testTogglePieces() {
        EditManager em = new EditManager();
        Piece p1 = new Piece("hello");
        Piece p2 = new Piece("my");
        Piece p3 = new Piece("name");
        LinkedList<Piece> pieces = new LinkedList<>(Arrays.asList(p1, p2, p3));

        assertFalse(em.togglePieces(pieces, new int[]{1}).get(1).isVisible());
        assertTrue(em.togglePieces(pieces, new int[]{1}).get(0).isVisible());
    }
}
