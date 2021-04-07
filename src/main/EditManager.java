package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

import static main.EditType.INSERT;

public class EditManager {
    private LinkedList<Piece> pieces = new LinkedList<Piece>();
    private LinkedList<Edit> edits = new LinkedList<Edit>();

    public EditManager() {
        pieces.add(new Piece(""));
    }

    public void insertString(int position, String s){
        //create new piece and put its id in a new piece id array
        Piece newPiece = new Piece(s);
        int[] newPieceArray = {newPiece.id()};

        //create new edit and insert it in edits linked list
        Edit newEdit = new Edit(INSERT, newPieceArray);
        edits.add(newEdit);

        FindResult result = findPiece(pieces, position);
        Piece[] splits = splitPiece(result.piece, result.index, 0);
        pieces = insertPiece(pieces, result.index, splits, newPiece);
    }

    public void delete(int position){
        //splitPiece(position, 1);
    }
    public LinkedList<Edit> getEdits(){
        return edits;
    }

    public String getText(){
        return pieces.stream().map(Piece::text).collect(Collectors.joining(" "));
    }

    public void undo(int[] editIds){

    }

    public void redo(int[] editIds){

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
        Piece curr;
        int pos = 0;
        int i = 0;
        do {
            curr = pieces.get(i);
            if (curr.isVisible()) {
                pos += curr.length();
            }
            i++;
        } while (i < pieces.size() && pos <= position);
        return new FindResult(curr, i - 1, curr.length() - pos + position);
    }

    protected Piece[] splitPiece(Piece piece, int position, int deleteLength) {
        String text = piece.text();
        ArrayList<Piece> pieces = new ArrayList<>();
        Piece pre = new Piece(text.substring(0, position), piece);
        pieces.add(pre);
        if (deleteLength > 0) {
            Piece deleted = new Piece(text.substring(position, position + deleteLength), piece);
            pieces.add(deleted);
        }
        if (position + deleteLength < text.length()) {
            Piece end = new Piece(text.substring(position + deleteLength), piece);
            pieces.add(end);
        }
        return pieces.toArray(new Piece[0]);
    }

    protected LinkedList<Piece> insertPiece(LinkedList<Piece> pieces, int index, Piece[] splits, Piece piece) {
        LinkedList<Piece> nextPieces = new LinkedList<>();
        for (int i = 0; i != pieces.size(); i++) {
            Piece p = pieces.get(i);
            if (i == index) {
                nextPieces.add(splits[0]);
                nextPieces.add(piece);
                for (int j = 1; j != splits.length; j++) {
                    nextPieces.add(splits[j]);
                }
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
