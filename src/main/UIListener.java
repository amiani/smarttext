package main;

import java.awt.event.ActionEvent;
import java.util.LinkedList;

import javax.swing.AbstractAction;

public class UIListener {

	EditManager em;
	GroupManager gm;

	public UIListener() {
		em = new EditManager();
		gm = new GroupManager();
	}

	// ERROR HANLING METHODS

	/**
	 * Checks array to ensure there are no negative values in the provided array
	 * 
	 * @param array
	 * @return
	 */

	private boolean checkNegativeArr(int[] array) {
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] < 0)
					return false;
			}
		}
		return true;
	}

	/**
	 * Checks value to ensure it is not negative. Implemented solely to make code
	 * more understandable as oppose to having if(value < 1) everywhere.
	 * 
	 * @param value
	 * @return
	 */

	private boolean checkNegativeVal(int value) {
		return value < 0 ? false : true;
	}

	/**
	 * Iterates through checkboxes and retrieves the Ids of those who are selected
	 * in the form of an integer array
	 * 
	 * @param checkboxes
	 * @return
	 */

	public int[] getSelectedIds(CheckBox[] checkboxes) {
		int[] ids;
		int selectedCheckboxes = 0;

		// Finds the size of the ids array by iterating through the checkboxes and
		// counting those which have been selected
		for (int j = 0; j < checkboxes.length; j++) {
			if (checkboxes[j].getSelected())
				selectedCheckboxes++;
		}

		ids = new int[selectedCheckboxes];
		int selectedId = 0;

		// Adds ids of the selected checkboxes into the ids array
		for (int i = 0; i < checkboxes.length; i++) {
			if (checkboxes[i].getSelected()) {
				ids[selectedId] = checkboxes[i].getEditId();
				selectedId++;
			}
		}

		return ids;
	}

	/**
	 * -----------------------------------------------------------------------------
	 * -------------------------------- LISTENERS ----------------------------------
	 * -----------------------------------------------------------------------------
	 * 
	 * These listeners are in the form of Action objects. This was for simplicity as
	 * the JSwing buttons can accept them as a single parameter and perform their
	 * designated operation when clicked. The listeners associated with the
	 * operations in the Model that require an integer array of ids ( undo(),
	 * redo(), deleteEdits(), etc. ) are initialized with an array of custom Jswing
	 * checkboxes. This was for testing purposes and I'm unsure how the UI will be
	 * implemented so I left them as they may be of use. These listeners can be
	 * easily reconfigured to be initialized with an integer array instead. If this
	 * is the case the only change that should be made is removing the getSelected()
	 * method and having the initalized integer array as the parameters in the
	 * checkNegativeArr() and the associated EditManager and GroupManager methods.
	 * 
	 */

	
	/**
	 * The Listener associated with the undo method in the EditManager
	 * 
	 * @author Brandon
	 */

	public class HandleUndoAction extends AbstractAction {

		CheckBox[] edits;
		int[] ids;

		HandleUndoAction(CheckBox[] edits) {
			this.edits = edits;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int[] ids = getSelectedIds(edits);
			if (checkNegativeArr(ids)) {

				System.out.println("Undo Handled ");
				em.undo(ids);

			} else {

			}
		}
	}

	/**
	 * The Listener associated with the redo method in the EditManager
	 * 
	 * @author Brandon
	 */

	public class HandleRedoAction extends AbstractAction {

		CheckBox[] edits;
		int[] ids;

		HandleRedoAction(CheckBox[] edits) {
			this.edits = edits;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int[] ids = getSelectedIds(edits);
			if (checkNegativeArr(ids)) {

				System.out.println("redo Handled ");
				em.redo(ids);

			} else {

			}
		}
	}

	
	/**
	 * The Listener associated with the deleteEdits method in the EditManager
	 * 
	 * @author Brandon
	 */

	public class HandleDeleteEditsAction extends AbstractAction {
		CheckBox[] edits;
		int[] ids;

		HandleDeleteEditsAction(CheckBox[] edits) {
			this.edits = edits;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int[] ids = getSelectedIds(edits);
			if (checkNegativeArr(ids)) {
				
				System.out.println("Delete Edits Handled");
				em.deleteEdits(ids);
				
			} else {

			}
		}
	}

	
	/**
	 * The Listener associated with the getEdits method in the EditManager
	 * 
	 * @author Brandon
	 */

	public class HandleGetEditsAction extends AbstractAction {

		LinkedList<Edit> edits;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			System.out.println("Get Edits Handled");
			edits = em.getEdits();
		}

		public LinkedList<Edit> getEdits() {
			return edits;
		}

	}

	
	/**
	 * The Listener associated with the CreateGroup method in the GroupManager
	 * 
	 * @author Brandon
	 */

	public class HandleCreateGroupAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			System.out.println("Create Group Handled");
			gm.createGroup();
		}
	}

	
	/**
	 * The Listener associated with the deleteGroup method in the GroupManager
	 * 
	 * @author Brandon
	 */

	public class HandleDeleteGroupAction extends AbstractAction {

		int groupId;

		public HandleDeleteGroupAction(int groupId) {
			this.groupId = groupId;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkNegativeVal(groupId)) {

				System.out.println("Delete Group Handled");
				gm.deleteGroup(groupId);

			} else {

			}
		}
	}

	
	/**
	 * The Listener associated with the addEdits method in the GroupManager
	 * 
	 * @author Brandon
	 */

	public class HandleAddEditAction extends AbstractAction {

		CheckBox[] edits;
		int groupId;

		public HandleAddEditAction(int groupId, CheckBox[] edits) {
			this.groupId = groupId;
			this.edits = edits;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int[] ids = getSelectedIds(edits);
			if (checkNegativeArr(ids) && checkNegativeVal(groupId)) {

				System.out.println("Add Edit Handled");
				gm.addEdits(groupId, ids);

			} else {

			}
		}
	}

	
	/**
	 * The Listener associated with the removeEdits method in the GroupManager
	 * 
	 * @author Brandon
	 */

	public class HandleRemoveEditAction extends AbstractAction {

		CheckBox[] edits;
		int[] ids;
		int groupId;

		public HandleRemoveEditAction(int groupId, CheckBox[] edits) {
			this.groupId = groupId;
			this.edits = edits;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int[] ids = getSelectedIds(edits);
			if (checkNegativeArr(ids) && checkNegativeVal(groupId)) {

				System.out.println("Remove Edit Handled");
				gm.removeEdits(groupId, ids);

			} else {

			}
		}
	}

	
	/**
	 * The Listener associated with the load method in the FileIO FileIO not yet
	 * implemented
	 * 
	 * @author Brandon
	 */

	public class HandleLoadAction extends AbstractAction {

		String filename;

		public HandleLoadAction(String filename) {
			this.filename = filename;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
//				handleLoad(filename);
		}
	}

	
	/**
	 * The Listener associated with the save method in the FileIO FileIO not yet
	 * implemented
	 * 
	 * @author Brandon
	 */

	public class HandleSaveAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
//				handleSave();
		}
	}
	
	
	
	/** ----------------------------------------------
	 * --------Methods for UIListenerTest-------------
	 * -----------------------------------------------
	 */
	
	
	public class HandleGetGroupsAction extends AbstractAction {

		LinkedList<Group> groups;

		@Override
		public void actionPerformed(ActionEvent e) {
			
			System.out.println("Get Groups Handled");
			groups = gm.getGroups();
			for(int i = 0; i< groups.size(); i++) {
				System.out.println(groups.get(i).getId());
			}
		}

		public LinkedList<Group> getGroups() {
			return groups;
		}

	}

	
	public void handleUndo(int[] editIds) {
		if (checkNegativeArr(editIds)) {

			System.out.println("Undo Handled ");
			em.undo(editIds);
			
			
		} else {

		}
	}

	
	public void handleRedo(int[] editIds) {
		if (checkNegativeArr(editIds)) {

			System.out.println("Redo Handled");
			em.redo(editIds);
			
		} else {

		}
	}
	
	public void handleDeleteEdits(int[] editIds) {
		if(checkNegativeArr(editIds)) {
			em.deleteEdits(editIds);
		}
		else {
			
		}
	}
	
	
	public void handleGetEdits() {
		em.getEdits();
	}

	
	public void handleCreateGroup() {
		gm.createGroup();
	}

	public void handleDeleteGroup(int groupId) {
		if (checkNegativeVal(groupId)) {

			System.out.println("Delete Group Handled");
			gm.deleteGroup(groupId);
			
		} else {

		}
	}

	
	public void handleAddEdit(int groupId, int[] editIds) {
		if (checkNegativeArr(editIds) && checkNegativeVal(groupId)) {

			System.out.println("Add Edit Handled");
			gm.addEdits(groupId, editIds);
			
		} else {

		}
	}
	

	public void handleRemoveEdit(int groupId, int[] editIds) {
		if (checkNegativeArr(editIds) && checkNegativeVal(groupId)) {

			System.out.println("Remove Edit Handled");
			gm.removeEdits(groupId, editIds);
			
		} else {

		}
	}

	
	public void handleSave() {

	}

	
	public void handleLoad(String fileName) {

	}
	
	public LinkedList<Group> handleGetGroups(){
		return gm.getGroups();
	}

}
