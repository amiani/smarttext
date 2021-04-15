package main;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.LinkedList;

public class GroupManager implements DocumentListener, createDeleteGroup, modifyGroup{
    private LinkedList<Group> groups;
    public GroupManager(){
        groups = new LinkedList<Group>();
    }

    public int createGroup(){
        //find id of the last element and add +1 to the value to get new id
        int lastElementId = groups.getLast().id();
        Group newGroup = new Group(lastElementId+1);
        groups.add(newGroup);
        return lastElementId+1;
    }

    public void deleteGroup(int groupId){
        //iterate to find group with matching group id, and then delete id
        for(int i=0;i<groups.size();i++){
            if(groups.get(i).id() == groupId){
                groups.remove(i);
                return;
            }
        }
        return;
    }

    public void addEdits(int groupId, int[] editIds){
        //iterate to find group with matching group id, and then add edits to it
        for(int i=0;i<groups.size();i++){
            if(groups.get(i).id() == groupId){
                groups.get(i).addEdits(editIds);
                return;
            }
        }
        return;
    }

    public void removeEdits(int groupId, int[] editIds){
        //iterate to find group with matching group id, and then remove edits from it
        for(int i=0;i<groups.size();i++){
            if(groups.get(i).id() == groupId){
                groups.get(i).removeEdits(editIds);
                return;
            }
        }
        return;
    }
    public void insertUpdate(DocumentEvent e) {

    }

    public void removeUpdate(DocumentEvent e) {

    }

    public void changedUpdate(DocumentEvent e) {}
}
