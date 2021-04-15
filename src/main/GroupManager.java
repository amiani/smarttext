package main;

import java.util.LinkedList;
import java.util.NoSuchElementException;

public class GroupManager implements createDeleteGroup, modifyGroup{
    private LinkedList<Group> groups;
    public GroupManager(){
        groups = new LinkedList<Group>();
    }

    public LinkedList<Group> getGroups(){
    	return groups;
    }

    public LinkedList<Integer> getEditList(int groupindex){
    	  for(int i=0;i<groups.size();i++){
              if(groups.get(i).id() == groupindex){
                 return groups.get(i).edits();
              }

    	  }
    	  System.out.println("Error group not found. returning null");
    	  return null;
    }

    public int createGroup(){
        //find id of the last element and add +1 to the value to get new id
    	try {
        int lastElementId = groups.getLast().id();
        Group newGroup = new Group(lastElementId+1);
        groups.add(newGroup);
        return lastElementId+1;
    	}catch(NoSuchElementException e) {
    		System.out.println("No last element. Creating new initial group");
    		Group defaultGroup = new Group(0, new int[]{});
    		groups.add(defaultGroup);
    		return 0;
    	}
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
            if(groups.get(i).id() == groupId){
                groups.remove(i);

                updateIds();
                return;
            }
        }
        return;
    }
    public void addEdits(int groupId, int[] editIds){
        if (groupId < 0) {
            return;
        }
        //iterate to find group with matching group id, and then add edits to it
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).id() == groupId){

            	groups.get(i).addEdits(editIds);
                return;
            }
        }
        return;
    }

    public void updateIds() {
    	for(int i=0;i<groups.size();i++) {
    		groups.get(i).setId(i);
    	}
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
}