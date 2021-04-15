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

    public void delete(int position, int deleteLength) {
        pieces = deleteText(pieces, position, deleteLength);
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
    	System.out.println(Arrays.toString(editIds));
        pieces = togglePieces(pieces, toPieceIds(edits, editIds));
    }
    public void redo(int[] editIds){
        undo(editIds);
    }

    public void deleteEdits(int[] editIds){
        edits = removeEdits(edits, editIds);
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
            return replacePieces(pieces, result.index, replacement);
        } else {
            LinkedList<Piece> nextPieces = new LinkedList<>();
            nextPieces.add(piece);
            return nextPieces;
        }
    }

    protected LinkedList<Piece> deleteText(List<Piece> pieces, int position, int deleteLength) {
        ArrayList<FindResult> results = findPieces(pieces, position, deleteLength)
                .collect(Collectors.toCollection(ArrayList<FindResult>::new));

        ArrayList<ArrayList<Piece>> splits = results.stream()
                .map(res -> splitPiece(res.piece, res.piecePosition, res.splitLength))
                .collect(Collectors.toCollection(ArrayList<ArrayList<Piece>>::new));

        int[] pieceIds;
        if (splits.get(0).size() == 1) {
            pieceIds = new int[]{splits.get(0).get(0).id()};
        } else {
            int pre = splits.get(0).get(1).id();
            int[] mid;
            if (splits.size() > 2) {
                mid = splits.subList(1, splits.size() - 1).stream()
                        .mapToInt(s -> s.get(0).id()).toArray();
            } else {
                mid = new int[0];
            }
            int post = splits.get(splits.size() - 1).get(0).id();
            pieceIds = new int[mid.length + 2];
            pieceIds[0] = pre;
            for (int i = 0; i != mid.length; i++) {
                pieceIds[i + 1] = mid[i];
            }
            pieceIds[pieceIds.length - 1] = post;
        }
        Edit edit = new Edit(DELETE, pieceIds);
        edits.add(edit);

        int[] indexes = results.stream().mapToInt(r -> r.index).toArray();
        return replacePieces(pieces, indexes, splits);
    }

    protected int[] toPieceIds(List<Edit> edits, int[] editIds) {
        return edits.stream()
                .filter(e -> Arrays.stream(editIds).anyMatch(id -> id == e.id()))
                .flatMapToInt(e -> IntStream.of(e.pieces()))
                .distinct()
                .toArray();
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
        int splitLength = Math.min(deleteLength, curr.length() - piecePosition);
        results.add(new FindResult(curr, i - 1, piecePosition, splitLength));
        deleteLength -= curr.length() - piecePosition;
        while (i < pieces.size() && deleteLength > 0) {
            curr = pieces.get(i);
            if (curr.isVisible()) {
                splitLength = Math.min(deleteLength, curr.length());
                results.add(new FindResult(curr, i, 0, splitLength));
                deleteLength -= curr.length();
            }
            i++;
        }
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

    protected LinkedList<Piece> replacePieces(List<Piece> pieces, int[] indexes, ArrayList<ArrayList<Piece>> splits) {
        if (indexes.length != splits.size()) {
            System.out.println("indexes not same size as splits");
        }

        LinkedList<Piece> nextPieces = new LinkedList<>();
        for (int i = 0, j = 0; j != pieces.size(); j++) {
            Piece p = pieces.get(j);
            int currIndex;
            if (i < indexes.length) {
                currIndex = indexes[i];
            } else {
                currIndex = -1;
            }
            if (j == currIndex) {
                nextPieces.addAll(splits.get(i));
                i++;
            } else {
                nextPieces.add(p);
            }
        }
        return nextPieces;
    }

    protected LinkedList<Piece> replacePieces(List<Piece> pieces, int index, ArrayList<Piece> splits) {
        return replacePieces(pieces, new int[]{index}, new ArrayList<>(Collections.singleton(splits)));
    }
    

    
}



class FindResult {
    public final Piece piece;
    public final int index;
    public final int piecePosition;
    public final int splitLength;
    public FindResult(Piece piece, int index, int piecePosition, int splitLength) {
        this.piece = piece;
        this.index = index;
        this.piecePosition = piecePosition;
        this.splitLength = splitLength;
    }
}