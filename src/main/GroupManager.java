package main;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.LinkedList;

public class GroupManager implements createDeleteGroup, modifyGroup{
    private LinkedList<Group> groups;
    public GroupManager(){
        groups = new LinkedList<Group>();
    }

    public int createGroup(){
        //find id of the last element and add +1 to the value to get new id
        int newId = 0;
        if(groups.size() != 0){
            newId = groups.getLast().getId();
        }

        Group newGroup = new Group(newId+1);
        groups.add(newGroup);
        return newId+1;
    }

    public Group findFromIndex(int findIndex){
        if(groups.size() >= findIndex){
            return groups.get(findIndex);
        }
        return null;
    }

    public void deleteGroup(int groupId){
        //iterate to find group with matching group id, and then delete id
        for(int i=0;i<groups.size();i++){
            if(groups.get(i).getId() == groupId){
                groups.remove(i);
                return;
            }
        }
        return;
    }
    public void addEdits(int groupId, int[] editIds){
        //iterate to find group with matching group id, and then add edits to it
        if(groupId<0){
    return;
        }
        for(int i=0;i<groups.size();i++){
            if(groups.get(i).getId() == groupId){
                groups.get(i).addEdits(editIds);
                return;
            }
        }
        return;
    }

    public void removeEdits(int groupId, int[] editIds){
        //iterate to find group with matching group id, and then remove edits from it
        for(int i=0;i<groups.size();i++){
            if(groups.get(i).getId() == groupId){
                groups.get(i).removeEdits(editIds);
                return;
            }
        }
        return;
    }
}
