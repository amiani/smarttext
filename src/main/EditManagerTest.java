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
}
