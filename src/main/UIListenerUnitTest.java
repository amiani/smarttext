package main;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.junit.jupiter.api.Test;

import main.UIListener.HandleAddEditAction;
import main.UIListener.HandleSaveAction;

class UIListenerUnitTest {
	
	EditManager em = new EditManager();
	GroupManager gm = new GroupManager();
	int[] edits = new int[0];
	JTextArea area = new JTextArea();
	JList editList = new JList();
	JList groupList = new JList();
	JList groupContentList = new JList();
	
	UIListener listener = new UIListener(em, gm, edits, area, editList, groupList, groupContentList);
	
	@Test
	void testCheckNegativeArray() {

		int[] testArr1 = {2, 18, 12};
		int[] testArr2 = {4, 6, 17, 72};
		int[] testArr3 = {1, 32, 8, 17, 44};
		int[] testArr4 = {23, 43, 6, 19, 15, 33};
		int[] testArr5 = {18, 7, 13, 98, 120, 53, 5};
		int[] testArr6 = {37, 92, 55, 71, 43, 16, 85, 7};
		int[] testArr7 = {3, 34, 2, 9, 14, 21, 57, 22, 64}; 
		int[] testArr8 = {85, 75, 90, 10, 11, 81, 34, 8, 25, 66}; 

	
		boolean actualOutput = true;

		assertEquals(listener.checkNegativeArr(testArr1), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr2), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr3), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr4), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr5), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr6), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr7), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr8), actualOutput);
		

		int[] testArr9 = {-2, 18, 12};
		int[] testArr10 = {4, 6, 17, -72};
		int[] testArr11 = {1, 32, -8, 17, 44};
		int[] testArr12 = {23, -43, 6, -19, 15, 33};
		int[] testArr13 = {18, 7, 13, 98, -120, 53, 5};
		int[] testArr14 = {37, 92, 55, -71, -43, -16, -85, 7};
		int[] testArr15 = {3, 34, -2, 9, -14, -21, 57, -22, 64}; 
		int[] testArr16 = {85, -75, 90, -10, 11, 81, 34, 8, 25, -66};
		
		
		actualOutput = false;

		assertEquals(listener.checkNegativeArr(testArr9), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr10), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr11), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr12), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr13), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr14), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr15), actualOutput);
		assertEquals(listener.checkNegativeArr(testArr16), actualOutput);

	}
	
	
	public void checkNegativeValTest() {
		
		int value1 = 4;
		int value2 = 3;
		int value3 = 5;
		int value4 = 10;
		int value5 = 101;
		int value6 = 72;
		int value7 = 9;
		int value8 = 24;
		
		boolean actualOutput = true;
		
		assertEquals(listener.checkNegativeVal(value1), actualOutput);
		assertEquals(listener.checkNegativeVal(value2), actualOutput);
		assertEquals(listener.checkNegativeVal(value3), actualOutput);
		assertEquals(listener.checkNegativeVal(value4), actualOutput);
		assertEquals(listener.checkNegativeVal(value5), actualOutput);
		assertEquals(listener.checkNegativeVal(value6), actualOutput);
		assertEquals(listener.checkNegativeVal(value7), actualOutput);
		assertEquals(listener.checkNegativeVal(value8), actualOutput);

		
		value1 = -4;
		value2 = -3;
		value3 = -5;
		value4 = -10;
		value5 = -101;
		value6 = -72;
		value7 = -9;
		value8 = -24;
		
		actualOutput = false;
		
		assertEquals(listener.checkNegativeVal(value1), actualOutput);
		assertEquals(listener.checkNegativeVal(value2), actualOutput);
		assertEquals(listener.checkNegativeVal(value3), actualOutput);
		assertEquals(listener.checkNegativeVal(value4), actualOutput);
		assertEquals(listener.checkNegativeVal(value5), actualOutput);
		assertEquals(listener.checkNegativeVal(value6), actualOutput);
		assertEquals(listener.checkNegativeVal(value7), actualOutput);
		assertEquals(listener.checkNegativeVal(value8), actualOutput);
		
	}	
	
	
	public static void main(String[] args) {
		new UIListenerButtonUnitTest();

	}

}

class UIListenerButtonUnitTest {
	int count = 0;
	JLabel label;
	EditManager em = new EditManager();
	GroupManager gm = new GroupManager();
	int[] edits = new int[0];
	JTextArea area = new JTextArea();
	JList editList = new JList();
	JList groupList = new JList();
	JList groupContentList = new JList();
	
	UIListener listener = new UIListener(em, gm, edits, area, editList, groupList, groupContentList);
	
	
	public UIListenerButtonUnitTest() {
		
		
		
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		
		area = new JTextArea();
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.add(area);
	    frame.setSize(10, 10);
	        frame.setVisible(true);
		
		
		JLabel undoEdit = new JLabel("Undo Edit");
		JLabel addGroupEdit = new JLabel("Add Edits To Group");
		JLabel removeGroupEdit = new JLabel("Remove Edits From Group");
		JLabel deleteEdits = new JLabel("Delete Edits");
		JLabel createGroup = new JLabel("Create Group");
		JLabel deleteGroup = new JLabel("Delete Groups");
		JLabel save = new JLabel("Save");
		JLabel load = new JLabel("Load");
		JLabel newText = new JLabel("New");
		JLabel quit = new JLabel("Quit");
		
		
		JButton buttonUndoEdit = new JButton();
		JButton buttonAddGroupEdit = new JButton();
		JButton buttonRemoveGroupEdit = new JButton();		
		JButton buttonDeleteEdits = new JButton();
		JButton buttonCreateGroup = new JButton();
		JButton buttonDeleteGroup = new JButton();
		JButton buttonSave = new JButton();
		JButton buttonLoad = new JButton();
		JButton buttonNew = new JButton();
		JButton buttonQuit = new JButton();
		
		int[] testArr = {4, 17, 12};
		int testValue = 1;
		
		buttonUndoEdit.addActionListener(listener.new HandleUndoAction(testArr)); 
		buttonAddGroupEdit.addActionListener(listener.new HandleAddEditAction(testArr, testValue));
		buttonRemoveGroupEdit.addActionListener(listener.new HandleRemoveEditAction(testValue, testArr));
		buttonDeleteEdits.addActionListener(listener.new HandleDeleteEditsAction(testArr));
		buttonCreateGroup.addActionListener(listener.new HandleCreateGroupAction());
		buttonDeleteGroup.addActionListener(listener.new HandleDeleteGroupAction(testValue));
		buttonSave.addActionListener(listener.new HandleSaveAction(area));
		buttonLoad.addActionListener(listener.new HandleLoadAction(area));
		buttonNew.addActionListener(listener.new HandleNewAction(area));
		buttonQuit.addActionListener(listener.new HandleQuitAction());
		

		

		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel.setLayout(new GridLayout(0, 1));;
		panel.add(undoEdit);
		panel.add(buttonUndoEdit);
		panel.add(addGroupEdit);
		panel.add(buttonAddGroupEdit);
		panel.add(removeGroupEdit);
		panel.add(buttonRemoveGroupEdit);
		panel.add(deleteEdits);
		panel.add(buttonDeleteEdits);
		panel.add(createGroup);
		panel.add(buttonCreateGroup);
		panel.add(deleteGroup);
		panel.add(buttonDeleteGroup);
		panel.add(save);
		panel.add(buttonSave);
		panel.add(load);
		panel.add(buttonLoad);
		panel.add(newText);
		panel.add(buttonNew);
		panel.add(quit);
		panel.add(buttonQuit);


		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Test UI Listener");
		frame.pack();
		frame.setVisible(true);
		


	}

	

}
