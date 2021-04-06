package main;

import java.util.LinkedList;

public class EditManager {
    private LinkedList<Piece> pieces = new LinkedList<Piece>();
    private LinkedList<Edit> edits = new LinkedList<Edit>();

    public EditManager(){

    }
    public void insert(int position, char c){

        //find new id for new piece by finding greatest id value
        int lastElementPieceId = 0;
        for(int i = 0; i < pieces.size();i++){
            lastElementPieceId = Math.max(lastElementPieceId, pieces.get(i).getId());
        }

        //find new id for new edit
        int lastElementEditId = edits.getLast().getId();

        //create new piece and put its id in a new piece id array
        Piece newPiece = new Piece(lastElementPieceId+1, c);
        int[] newPieceArray = {newPiece.getId()};

        //create new edit and insert it in edits linked list
        Edit newEdit = new Edit(lastElementEditId+1, newPieceArray);
        edits.add(newEdit);


        int splittedIndex = splitPiece(position, 0);
        pieces.add(splittedIndex, newPiece);

        return;
    }
    public void delete(int position){
        splitPiece(position, 1);
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

    public int splitPiece(int position, int deleteLength){

    }
}
