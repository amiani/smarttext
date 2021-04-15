package main;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
//import java.awt.event.ActionEvent;
//import java.util.ArrayList;
//import javax.swing.AbstractAction;
import javax.swing.JCheckBox;

/** Custom made Checkbox that extends the checkbox from Jswing. Can be discarded/replaced but I kept it because it was used for testing
 *  and it can possibly be useful for the UI. Certain listeners rely on these checkboxes so if this is discarded keep that in mind,
 *  however those listeners can be easily reconfigured to work with other Jswing elements. 
 * 
 * @author Brandon
 *
 */

class CheckBox extends JCheckBox{

	Edit edit;
	Group group;
	boolean selected;
	CheckBoxListener listener = new CheckBoxListener();
	
	public CheckBox(Edit edit, Group group) {
		super();
		this.group = group;
		this.edit = edit;
		this.addItemListener(listener);
	}
	
	public boolean getSelected() {
		selected = listener.getSelected();
		return selected;
	}
	
	public int getEditId() {
		return edit.id();
	}
	
	public int getGroupId() {
		return group.getId();
	}
	
}

/** This is the Listener for the custom CheckBox above. Its here so the state of the checkbox (selected/deselected) can be retrieved
 * 	and used with certain listeners
 * @author Brandon
 *
 */

class CheckBoxListener implements ItemListener{

	int id;
	boolean selected;
	
	public int getId() {
		return id;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			selected = true;
			System.out.println("(CheckBox.java) CheckBox Selected");
		}
		else selected = false;	
	}
	
	public boolean getSelected() {
		return selected;
	}
}

