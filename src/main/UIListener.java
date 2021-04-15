package main;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileSystemView;

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

	public boolean checkNegativeArr(int[] array) {
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

	public boolean checkNegativeVal(int value) {
		return value < 0 ? false : true;
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

		int[] edits;

		HandleUndoAction(int[] edits) {
			this.edits = edits;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkNegativeArr(edits)) {

				System.out.println("Undo Handled ");
				em.undo(edits);

			} else {

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
		int[] edits;

		HandleDeleteEditsAction(int[] edits) {
			this.edits = edits;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkNegativeArr(edits)) {

				System.out.println("Delete Edits Handled");
				em.deleteEdits(edits);

			} else {

			}
		}
	}

	/**
	 * The Listener associated with the getEdits method in the EditManager
	 * 
	 * @author Brandon
	 */

	public class HandleGetEditsAction implements ActionListener {

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

	public class HandleCreateGroupAction implements ActionListener {

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

	public class HandleDeleteGroupAction implements ActionListener {

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

		int[] edits;
		int groupId;

		public HandleAddEditAction(int[] edits, int groupId) {
			this.groupId = groupId;
			this.edits = edits;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (checkNegativeArr(edits) && checkNegativeVal(groupId)) {

				System.out.println("Add Edit Handled");
				gm.addEdits(groupId, edits);

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
		int returnValue = 0;
		String ingest;
		JTextArea area;

		String filename;

		public HandleLoadAction(JTextArea area) {
			this.area = area;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Choose destination.");
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File f = new File(jfc.getSelectedFile().getAbsolutePath());
				try {
					FileReader read = new FileReader(f);
					Scanner scan = new Scanner(read);
					while (scan.hasNextLine()) {
						String line = scan.nextLine() + "\n";
						if(ingest == null) {
							ingest = line;
						}
						else ingest = ingest + line;
					}
					area.setText(ingest);
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	/**
	 * The Listener associated with the save method in the FileIO FileIO not yet
	 * implemented
	 * 
	 * @author Brandon
	 */

	public class HandleSaveAction implements ActionListener {

		int returnValue;
		JTextArea area;

		public HandleSaveAction(JTextArea area) {
			this.area = area;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
			jfc.setDialogTitle("Choose destination.");
			jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			returnValue = jfc.showSaveDialog(null);
			try {
				File f = new File(jfc.getSelectedFile().getAbsolutePath());
				FileWriter out = new FileWriter(f);
				out.write(area.getText());
				out.close();
			} catch (FileNotFoundException ex) {
				Component f = null;
				JOptionPane.showMessageDialog(f, "File not found.");
			} catch (IOException ex) {
				Component f = null;
				JOptionPane.showMessageDialog(f, "Error.");
			} catch (NullPointerException ex) {
			}
		}
	}
	
	
	
	public class HandleNewAction implements ActionListener {

		JTextArea area;
		
		public HandleNewAction(JTextArea area) {
			this.area = area;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("New Handled");
			area.setText("");
		}	
	}
	
	
	
	public class HandleQuitAction implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Quit Handled");
			System.exit(0);
		}
		
	}

}
