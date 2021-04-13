package main;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static main.EditType.DELETE;
import static main.EditType.INSERT;

public class EditManager {
    private LinkedList<Piece> pieces = new LinkedList<>();
    private LinkedList<Edit> edits = new LinkedList<>();

    public void insert(int position, String s){
        Piece piece = new Piece(s);
        Edit newEdit = new Edit(INSERT, new int[]{piece.id()});
        edits.add(newEdit);
        pieces = insertPiece(pieces, position, piece);
    }

    protected LinkedList<Piece> insertPiece(List<Piece> pieces, int position, Piece piece) {
        if (pieces.size() > 0) {
            FindResult result = findPiece(pieces, position);
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
        FindResult result = findPiece(pieces, position);
        ArrayList<Piece> splits = splitPiece(result.piece, result.piecePosition, deleteLength);
        int[] pieceIds = splits.stream().mapToInt(Piece::id).toArray();
        Edit edit = new Edit(DELETE, pieceIds);
        edits.add(edit);
        pieces = replacePiece(pieces, result.index, splits);
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
        IntStream ids = Arrays.stream(editIds);
        List<Integer> pieceIds = edits.stream()
                .filter(e -> ids.anyMatch(i -> i == e.id()))
                .flatMapToInt(e -> IntStream.of(e.pieces()))
                .boxed().collect(Collectors.toList());
        pieces = togglePieces(pieces, pieceIds);
    }

    public void redo(int[] editIds){
        undo(editIds);
    }

    protected LinkedList<Piece> togglePieces(LinkedList<Piece> pieces, List<Integer> pieceIds) {
        return pieces.stream()
                .map(p -> {
                    Piece q = new Piece(p);
                    if (pieceIds.contains(p.id())) {
                        q.toggleVisible();
                    }
                    return q; })
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void deleteEdits(int[] editIds){
        edits = removeEdits(edits, editIds);
        /*
        int arraySize = editIds.length;
        int[] updatedEdits = editIds;

        //iterate all edit elements and compare their id with list of edit ids. Continue iterating until reach end of linked list,
        //or there are no more ids to delete
        for(int pos = 0;pos<edits.size() && arraySize>0;pos++){
            for(int j=0;j<updatedEdits.length;j++) {
                if(edits.get(pos).id() == updatedEdits[j]){
                    //if found matching id from edit id array and linked list, reduce the array size by -1 AND place the last valid id element in the position of the "matched" id
                    //hence, the list of ids to be searched will keep getting smaller.
                    arraySize--;
                    edits.remove(pos);
                    if(arraySize>0) {
                        updatedEdits[j] = updatedEdits[arraySize];
                    }
                    break;
                }
            }
        }
        return;
        */
    }

    protected LinkedList<Edit> removeEdits(List<Edit> edits, int[] editIds) {
        return edits.stream()
                .filter(e -> !Arrays.asList(editIds).contains(e.id()))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    protected FindResult findPiece(List<Piece> pieces, int position) {
        Piece curr = pieces.get(0);
        int pos = 0;
        int i = 0;

        while (i < pieces.size() && pos <= position) {
            curr = pieces.get(i);
            if (curr.isVisible()) {
                pos += curr.length();
            }
            i++;
        }
        return new FindResult(curr, i - 1, curr.length() - pos + position);
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
