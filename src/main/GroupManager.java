package main;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.LinkedList;

public class GroupManager implements createDeleteGroup, modifyGroup{
    private LinkedList<Group> groups;
    private int lastElementID = 0;
    
    public GroupManager(){
        groups = new LinkedList<Group>();
        createGroup(); // default list
    }
    
    public LinkedList<Group> getGroups(){
        return groups;
    }
    
    public Group getGroup(String groupName){
    	for(int i=0;i<groups.size();i++)
            if(groups.get(i).toString() == groupName)
                return groups.get(i);
        return groups.get(0);
    }

    public int createGroup(){
        Group newGroup = new Group(lastElementID++);
        groups.add(newGroup);
        return lastElementID;
    }
 
    public int createGroup(String name){
        Group newGroup = new Group(lastElementID++, name);
        groups.add(newGroup);
        return lastElementID;
    }
    
    public Group getDefaultGroup() {
    	return groups.get(0);
    }

    public void deleteGroup(int groupId){
        //iterate to find group with matching group id, and then delete id
        for(int i=0;i<groups.size();i++){
            if(groups.get(i).getId() == groupId){
                groups.remove(i);
                return;
            }
        }
    }
    public void addEdits(int groupId, int[] editIds){
        //iterate to find group with matching group id, and then add edits to it
        for(int i=0;i<groups.size();i++){
            if(groups.get(i).getId() == groupId){
                groups.get(i).addEdits(editIds);
                return;
            }
        }
        if (groupId != 0)
        	groups.get(0).addEdits(editIds);
    }

    public void removeEdits(int groupId, int[] editIds){
        //iterate to find group with matching group id, and then remove edits from it
        for(int i=0;i<groups.size();i++){
            if(groups.get(i).getId() == groupId){
                groups.get(i).removeEdits(editIds);
                return;
            }
        }
    }
}
