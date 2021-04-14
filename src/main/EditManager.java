package main;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static main.EditType.DELETE;
import static main.EditType.INSERT;

public class EditManager {
    private LinkedList<Piece> pieces = new LinkedList<>();
    private LinkedList<Edit> edits = new LinkedList<>();

    public void insert(int position, String s) {
        Piece piece = new Piece(s);
        Edit newEdit = new Edit(INSERT, new int[]{piece.id()});
        edits.add(newEdit);
        pieces = insertPiece(pieces, position, piece);
    }

    protected LinkedList<Piece> insertPiece(List<Piece> pieces, int position, Piece piece) {
        if (pieces.size() > 0) {
            FindResult result = findPieces(pieces, position, 0).toArray(FindResult[]::new)[0];
            ArrayList<Piece> replacement = new ArrayList<>();
            ArrayList<Piece> splits = splitPiece(result.piece, result.piecePosition, 0);
            replacement.add(splits.get(0));
            replacement.add(piece);
            for (int i = 1; i != splits.size(); i++) {
                replacement.add(splits.get(i));
            }
            return replacePiece(pieces, result.index, replacement);
        } else {
            LinkedList<Piece> nextPieces = new LinkedList<>();
            nextPieces.add(piece);
            return nextPieces;
        }
    }

    public void delete(int position, int deleteLength) {
        pieces = deleteText(pieces, position, deleteLength);
    }

    protected LinkedList<Piece> deleteText(List<Piece> pieces, int position, int deleteLength) {
        /*
        ArrayList<FindResult> results = findPieces(pieces, position, deleteLength);
        ArrayList<Piece> splits = splitPiece(results.piece, results.piecePosition, deleteLength);
        int[] pieceIds = splits.stream().mapToInt(Piece::id).toArray();
        Edit edit = new Edit(DELETE, pieceIds);
        edits.add(edit);
        return replacePiece(pieces, results.index, splits);
         */
        return (LinkedList<Piece>) pieces;
    }

    public LinkedList<Edit> getEdits(){
        return edits;
    }

    public String getText(){
        return pieces.stream()
                .filter(Piece::isVisible)
                .map(Piece::text)
                .collect(Collectors.joining());
    }

    public void undo(int[] editIds){
        pieces = togglePieces(pieces, toPieceIds(edits, editIds));
    }

    protected int[] toPieceIds(List<Edit> edits, int[] editIds) {
        return edits.stream()
                .filter(e -> Arrays.stream(editIds).anyMatch(id -> id == e.id()))
                .flatMapToInt(e -> IntStream.of(e.pieces()))
                .distinct()
                .toArray();
    }

    public void redo(int[] editIds){
        undo(editIds);
    }

    protected LinkedList<Piece> togglePieces(List<Piece> pieces, int[] pieceIds) {
        return pieces.stream()
                .map(p -> {
                    Piece q = new Piece(p);
                    if (Arrays.stream(pieceIds).anyMatch(id -> id == p.id())) {
                        q.toggleVisible();
                    }
                    return q; })
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void deleteEdits(int[] editIds){
        edits = removeEdits(edits, editIds);
    }

    protected LinkedList<Edit> removeEdits(List<Edit> edits, int[] editIds) {
        LinkedList<Edit> collect = edits.stream()
                .filter(e -> Arrays.stream(editIds).noneMatch(id -> id == e.id()))
                .collect(Collectors.toCollection(LinkedList::new));
        return collect;
    }

    protected Stream<FindResult> findPieces(List<Piece> pieces, int position, int deleteLength) {
        Piece curr = pieces.get(0);
        int pos = 0;
        int i = 0;
        ArrayList<FindResult> results = new ArrayList<>();

        while (i < pieces.size() && pos <= position) {
            curr = pieces.get(i);
            if (curr.isVisible()) {
                pos += curr.length();
            }
            i++;
        }
        int piecePosition = curr.length() - pos + position;
        results.add(new FindResult(curr, i - 1, piecePosition));
        deleteLength -= curr.length() - piecePosition;
        while (i < pieces.size() && deleteLength > 0) {
            curr = pieces.get(i);
            if (curr.isVisible()) {
                results.add(new FindResult(curr, i, 0));
                deleteLength -= curr.length();
            }
            i++;
        }
        //Stream<FindResult> res = Stream.of(new FindResult(curr, i - 1, curr.length() - pos + position));
        return results.stream();
    }

    protected ArrayList<Piece> splitPiece(Piece piece, int position, int deleteLength) {
        String text = piece.text();
        ArrayList<Piece> pieces = new ArrayList<>();
        if (position > 0) {
           pieces.add(new Piece(text.substring(0, position), piece));
        }
        if (deleteLength > 0) {
            Piece deleted = new Piece(text.substring(position, position + deleteLength), piece);
            deleted.setVisible(false);
            pieces.add(deleted);
        }
        if (position + deleteLength < text.length()) {
            pieces.add(new Piece(text.substring(position + deleteLength), piece));
        }
        return pieces;
    }

    protected LinkedList<Piece> replacePiece(List<Piece> pieces, int index, ArrayList<Piece> replacement) {
        LinkedList<Piece> nextPieces = new LinkedList<>();
        for (int i = 0; i != pieces.size(); i++) {
            Piece p = pieces.get(i);
            if (i == index) {
                nextPieces.addAll(replacement);
            } else {
                nextPieces.add(p);
            }
        }
        return nextPieces;
    }
}

class FindResult {
    public final Piece piece;
    public final int index;
    public final int piecePosition;
    public FindResult(Piece piece, int index, int piecePosition) {
        this.piece = piece;
        this.index = index;
        this.piecePosition = piecePosition;
    }
}
