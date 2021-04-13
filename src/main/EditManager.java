package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.max;
import static main.EditType.INSERT;

public class EditManager {
    private LinkedList<Piece> pieces = new LinkedList<>();
    private LinkedList<Edit> edits = new LinkedList<>();

    public EditManager() {
        //pieces.add(new Piece(""));
    }

    public void insertString(int position, String s){
        //create new piece and put its id in a new piece id array
        Piece newPiece = new Piece(s);
        int[] newPieceArray = {newPiece.id()};

        //create new edit and insert it in edits linked list
        Edit newEdit = new Edit(INSERT, newPieceArray);
        edits.add(newEdit);

        if (pieces.size() > 0) {
            FindResult result = findPiece(pieces, position);
            ArrayList<Piece> replacement = new ArrayList<>();
            if (result.piece == null) {
                replacement.add(pieces.get(result.index));
                replacement.add(newPiece);
            } else {
                Piece[] splits = splitPiece(result.piece, result.index, 0);
                replacement.add(splits[0]);
                replacement.add(newPiece);
                for (int i = 1; i != splits.length; i++) {
                    replacement.add(splits[i]);
                }
            }
            pieces = insertPiece(pieces, result.index, replacement);
        } else {
            LinkedList<Piece> nextPieces = new LinkedList<>();
            nextPieces.add(newPiece);
            pieces = nextPieces;
        }
    }

    public void delete(int position, int deleteLength) {
        FindResult result = findPiece(pieces, position);
        Piece[] splits = splitPiece(result.piece, result.index, deleteLength);

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
        int arraySize = editIds.length;
        int[] updatedEdits = editIds;

        //iterate all edit elements and compare their id with list of edit ids. Continue iterating until reach end of linked list,
        //or there are no more ids to delete
        for(int pos = 0;pos<edits.size() && arraySize>0;pos++){
            for(int j=0;j<updatedEdits.length;j++) {
                if(edits.get(pos).getId() == updatedEdits[j]){
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
    }

    protected FindResult findPiece(LinkedList<Piece> pieces, int position) {
        Piece curr = null;
        int pos = 0;
        int i = 0;

        while (i < pieces.size() && pos < position) {
            curr = pieces.get(i);
            if (curr.isVisible()) {
                pos += curr.length();
            }
            i++;
        }
        if (pos <= position) {
            return new FindResult(null, max(0, i - 1), 0);
        } else {
            return new FindResult(curr, i - 1, curr.length() - pos + position);
        }
    }

    protected Piece[] splitPiece(Piece piece, int position, int deleteLength) {
        String text = piece.text();
        ArrayList<Piece> pieces = new ArrayList<>();
        Piece pre = new Piece(text.substring(0, position), piece);
        pieces.add(pre);
        if (deleteLength > 0) {
            Piece deleted = new Piece(text.substring(position, position + deleteLength), piece);
            deleted.setVisible(false);
            pieces.add(deleted);
        }
        if (position + deleteLength < text.length()) {
            Piece end = new Piece(text.substring(position + deleteLength), piece);
            pieces.add(end);
        }
        return pieces.toArray(new Piece[0]);
    }

    protected LinkedList<Piece> insertPiece(LinkedList<Piece> pieces, int index, ArrayList<Piece> replacement) {
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
