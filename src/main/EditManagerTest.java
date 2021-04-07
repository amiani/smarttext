package main;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditManagerTest {
    @Test
    public void testSplitPiece() {
        EditManager em = new EditManager();
        LinkedList<Piece> pieces = new LinkedList<>();
        assertEquals(pieces, em.splitPiece(pieces, 0, 0));
    }
}
