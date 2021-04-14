package main;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class UIListenerTest {
	int count = 0;
	JLabel label;

	public UIListenerTest() {

//		Test on HandleGetEditsAction, HandleRemoveEditsAction, HandleAddEditsAction		
//		
//		JFrame frame = new JFrame();
//
//		JPanel panel = new JPanel();
//
//		JLabel creategroup = new JLabel("Create Groups");
//		JLabel getgroups = new JLabel("Get Groups");
//
//		CheckBox[] checkArr = new CheckBox[5];
//		
//		for(int i = 0; i < 5; i++) {
//			checkArr[i] = new CheckBox(new Edit(EditType.INSERT, null), null);
//		}
//		
//		
//
//		UIListener listener = new UIListener();
//		
//		listener.handleCreateGroup();
//		listener.handleCreateGroup();
//		
//		UIListener.HandleAddEditAction addGroupEdit1 = listener.new HandleAddEditAction(listener.handleGetGroups().get(0).getId(), checkArr);
//		UIListener.HandleRemoveEditAction addGroupEdit2 = listener.new HandleRemoveEditAction(listener.handleGetGroups().get(1).getId(), checkArr);
//
//		
//		JButton buttonAddGroupEdit1 = new JButton(addGroupEdit1);
//		JButton buttonAddGroupEdit2 = new JButton(addGroupEdit2);		
//		JButton buttonGetGroups = new JButton(getGroups);
//
//
//		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
//		panel.setLayout(new GridLayout(0, 1));
//		panel.add(creategroup);
//		panel.add(buttonAddGroupEdit1);
//		panel.add(buttonAddGroupEdit2);
//		panel.add(getgroups);
//		panel.add(buttonGetGroups);
//		
//		for(int i = 0; i < 5; i++) {
//			panel.add(checkArr[i]);
//		}
//
//		frame.add(panel, BorderLayout.CENTER);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setTitle("Test UI Listener");
//		frame.pack();
//		frame.setVisible(true);
		
		JFrame frame = new JFrame();

		JPanel panel = new JPanel();

		JLabel creategroup = new JLabel("Create Groups");
		JLabel getgroups = new JLabel("Get Groups");

		CheckBox[] checkArr = new CheckBox[5];
		
		for(int i = 0; i < 5; i++) {
			checkArr[i] = new CheckBox(new Edit(EditType.INSERT, null), null);
		}
		
		

		UIListener listener = new UIListener();
		
		listener.handleCreateGroup();
		listener.handleCreateGroup();
		
		UIListener.HandleGetGroupsAction getGroups = listener.new HandleGetGroupsAction();
		UIListener.HandleCreateGroupAction CreateGroup = listener.new HandleCreateGroupAction();
		UIListener.HandleDeleteGroupAction DeleteGroup = listener.new HandleDeleteGroupAction(0);
		
		JButton buttonCreateGroup = new JButton(CreateGroup);
		JButton buttonDeleteGroup = new JButton(DeleteGroup);		
		JButton buttonGetGroups = new JButton(getGroups);


		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
		panel.setLayout(new GridLayout(0, 1));
		panel.add(creategroup);
		panel.add(buttonCreateGroup);
		panel.add(buttonDeleteGroup);
		panel.add(buttonGetGroups);
		
		frame.add(panel, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Test UI Listener");
		frame.pack();
		frame.setVisible(true);


	}

	public static void main(String[] args) {
		new UIListenerTest();

	}

}
