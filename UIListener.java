package main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

public class UIListener {

	EditManager em;
	GroupManager gm;
	private int[] edits;
	private int[] addedits;
	private static JTextArea area;
	private static JList editlist;
	private static JList grouplist;
	private static JList defaulteditlist;
	private static JList groupcontentlist;
	
	public UIListener(EditManager e,GroupManager g, int[] edits, JTextArea area, JList editlist, JList grouplist, JList defaulteditlist, JList groupcontentlist) {
		em = e;
		gm = g;
		this.edits = edits;
		addedits = new int[] {};
		
		this.area = area;
		this.editlist = editlist;
		this.grouplist = grouplist;
		this.defaulteditlist = defaulteditlist;
		this.groupcontentlist = groupcontentlist;
		
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

	public void setEdits(int[] e) {
		
		edits = e;
		
	}


	/**
	 * -----------------------------------------------------------------------------
	 * -------------------------------- LISTENERS ----------------------------------
	 * -----------------------------------------------------------------------------
	 */

	
	/**
	 * The Listener associated with the undo method in the EditManager
	 * 
	 * @author Brandon
	 */
	
	public class HandleUndoAction implements ActionListener {

		
		HandleUndoAction(int[] e) {
			edits = e;
		}
		
	//Added array length clause so edits must be selected for undo
		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkNegativeArr(UIListener.this.edits) && UIListener.this.edits.length != 0) {

				System.out.println("Undo Handled ");
		
				//setting Listener.active to false to prevent the EditListener from firing on the undo action
				Listener.setActive(false);
				//perform undo and update JTextArea
				em.undo(UIListener.this.edits);
				System.out.println(em.getText());
				area.replaceRange(em.getText(), 0, area.getText().length());
				//set Listener.active to true so the EditListener will accept edits again
				Listener.setActive(true);
				

			} else {
				System.out.println("No edit selected");
			}

			
		}
	}

	/**
	 * The Listener associated with the redo method in the EditManager
	 * 
	 * @author Brandon
	 */

	public class HandleRedoAction implements ActionListener {

		int[] edits;

		HandleRedoAction(int[] edits) {
			this.edits = edits;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkNegativeArr(edits)) {
				System.out.println("redo Handled ");
				em.redo(edits);

			} else {

			}
		}
	}

	
	/**
	 * The Listener associated with the deleteEdits method in the EditManager
	 * 
	 * @author Brandon
	 */

	public class HandleDeleteEditsAction implements ActionListener {
	

		HandleDeleteEditsAction(int[] e) {
			edits = e;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkNegativeArr(UIListener.this.edits)) {
				
				System.out.println("Delete Edits Handled");
				em.deleteEdits(UIListener.this.edits);
				editlist.setListData(Listener.getActiveList().toArray());
				
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
			grouplist.setListData(gm.getGroups().toArray());
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

	public class HandleAddEditAction implements ActionListener {

		int groupId;

		public HandleAddEditAction(int[] edits, int groupId) {
			this.groupId = groupId;
			addedits = edits;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkNegativeArr(UIListener.this.edits) && checkNegativeVal(groupId)) {

				System.out.println("Add Edit Handled");
				gm.addEdits(groupId, UIListener.this.addedits);
				System.out.println(groupId);
				System.out.println(UIListener.this.addedits);
				System.out.println(defaulteditlist.getSelectedIndices().toString());
				System.out.println(Listener.getActiveList().toString());
				groupcontentlist.setListData(Listener.getActiveList().toArray());

			} else {

			}
		}
	}

	
	/**
	 * The Listener associated with the removeEdits method in the GroupManager
	 * 
	 * @author Brandon
	 */

	public class HandleRemoveEditAction implements ActionListener {

		int[] edits;
		int groupId;

		public HandleRemoveEditAction(int groupId, int[] edits) {
			this.groupId = groupId;
			this.edits = edits;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkNegativeArr(edits) && checkNegativeVal(groupId)) {

				System.out.println("Remove Edit Handled");
				gm.removeEdits(groupId, edits);

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
		
		}
	}
	
}