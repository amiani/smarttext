package main;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditManagerTest {
    @Test
    public void testFindPiece() {
        EditManager em = new EditManager();
        Piece p = new Piece("hello");
        LinkedList<Piece> pieces = new LinkedList<>(Arrays.asList(
                new Piece("hello")));
        assertEquals(p, em.findPiece(pieces, 0));
    }
}
