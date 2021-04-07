package main;

import java.util.LinkedList;

import static main.EditType.INSERT;

public class EditManager {
    private LinkedList<Piece> pieces = new LinkedList<Piece>();
    private LinkedList<Edit> edits = new LinkedList<Edit>();

    public void insertString(int position, String s){
        //create new piece and put its id in a new piece id array
        Piece newPiece = new Piece(s);
        int[] newPieceArray = {newPiece.id()};

        //create new edit and insert it in edits linked list
        Edit newEdit = new Edit(INSERT, newPieceArray);
        edits.add(newEdit);


        //int splittedIndex = splitPiece(position, 0);
        //pieces.add(splittedIndex, newPiece);

        //create newpiece
        //create Edit referring to newpiece
        //find piece at position
        //split it, update Edit to refer to both pieces
        //insert newpiece at split
    }

    public void delete(int position){
        //splitPiece(position, 1);
    }
    public LinkedList<Edit> getEdits(){
        return edits;
    }

    public String getText(){
        return "";
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

    protected Piece findPiece(LinkedList<Piece> pieces, int position) {
        Piece curr = pieces.get(0);
        int pos = curr.length();
        int i = 1;
        while (i <= pieces.size() && pos <= position) {
            curr = pieces.get(i);
            pos += curr.length();
            i++;
        }
        return curr;
    }

    protected Piece[] splitPiece(Piece piece, int position, int deleteLength) {
        String text = piece.text();
        Piece first = new Piece(text.substring(0, position), piece);
        if (position + deleteLength < text.length()) {
            Piece second = new Piece(text.substring(position + deleteLength), piece);
            return new Piece[]{first, second};
        } else {
            return new Piece[]{first};
        }
    }
}
