package main;



/*TODO
 *	- add multiple groups to editlist so the grouplist will toggle between them
 *	- add linkedlist functionality---may be a UI listener issue?
 *
 */
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import java.util.LinkedList;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class UserInterface extends JFrame implements ActionListener, ListSelectionListener{
	private static JPanel textarea;
	private static JPanel editarea;
	private static JPanel editoptions;
	private static JButton undobutton;
	private static JButton delbutton;
	private static JButton groupbutton;
	private static JButton managebutton;
	private static JButton addtogroup;
	private static JButton remfromgroup;
	private static JButton deletegroup;
	private static JScrollPane editscroll;
	private static JList editlist;
	private static JList grouplist;
	private static JTextArea area;
	private static JFrame frame;
	private static int returnValue = 0;
	
	//Default group + active group are only really here for testing. Will be replaced by edit groups on implementation
	private static LinkedList<String> defaultgroup;
	private static LinkedList<String> activegroup;
	
	
	public UserInterface() { run(); }
	
	public void run() {
		frame = new JFrame("SmartText");
		frame.setPreferredSize(new Dimension(800, 400));

	    // Set the look-and-feel (LNF) of the application
	        // Try to default to whatever the host system prefers
	    try {
	      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	      Logger.getLogger(UserInterface.class.getName()).log(Level.SEVERE, null, ex);
	    }
		
	    
	    //Creation of the text field
	    textarea = new JPanel(new BorderLayout());
	    textarea.setBorder(new TitledBorder ( new EtchedBorder(), "Text Area"));
		area = new JTextArea();
		JScrollPane textscroll = new JScrollPane();
		textscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textscroll.setViewportView(area);
		
		
		//Creation of edit sidebar
		editarea = new JPanel(new BorderLayout());
		editarea.setBorder(new TitledBorder ( new EtchedBorder(), "Edit Area"));
		
		JLabel groupLabel = new JLabel("Edit Group:");
		JLabel editLabel = new JLabel("Edits:");
		
		//Need to link UI to editmanager functionality. Testcases for now.
		
		//Only here to avoid crashing while testing*** Delete later
		defaultgroup = new LinkedList<String>();
		/*
		defaultgroup.add("aaa");
		defaultgroup.add("bbb");
		defaultgroup.add("ccc");
		defaultgroup.add("ddd");
		defaultgroup.add("eee");
		defaultgroup.add("fff");
		*/
		//Plan for defaultgroup to hold all edits
		
		
		activegroup = defaultgroup;
		
		
		editlist= new JList(activegroup.toArray());
		editlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		editscroll = new JScrollPane();
		
		//Need to add 
		
		String testgroups[] = {"Default Group"};
		
		grouplist = new JList(testgroups);
		JScrollPane groupscroll = new JScrollPane();
		editscroll.setViewportView(editlist);
		groupscroll.setViewportView(grouplist);
		editscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		groupscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	
		grouplist.addListSelectionListener(this);
		
		
		
		JPanel listarea = new JPanel();
		
		listarea.add(groupLabel, BorderLayout.CENTER);
		listarea.add(groupscroll, BorderLayout.CENTER);
		listarea.add(editLabel, BorderLayout.CENTER);
		listarea.add(editscroll, BorderLayout.CENTER);
		
		editarea.add(listarea);
		
		editoptions = new JPanel(new GridLayout(0,2));
		editoptions.setBorder(new TitledBorder ( new EtchedBorder(), "Edit Options"));
		undobutton = new JButton("Undo");
		groupbutton = new JButton("Create Group");
		delbutton = new JButton("Delete Edit");
		managebutton = new JButton("Manage Group");
		deletegroup = new JButton("Delete Group");
		
		groupbutton.addActionListener(this);
		managebutton.addActionListener(this);
		
		editoptions.add(undobutton);
		editoptions.add(delbutton);
		editoptions.add(groupbutton);
		editoptions.add(managebutton);
		editoptions.add(deletegroup);
		
		
		
		
		//Need to add editoptions below the JList components
		editarea.add(editoptions, BorderLayout.SOUTH);
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.add(textscroll);
		frame.add(editarea, BorderLayout.WEST);
		frame.pack();
		frame.setLocationRelativeTo(null);
			frame.setVisible(true);
			
			
		//Menu bar included from example text editor
		JMenuBar menu_main = new JMenuBar();
		
			JMenu menu_file = new JMenu("File");
			
			JMenuItem menuitem_new = new JMenuItem("New");
			JMenuItem menuitem_open = new JMenuItem("Open");
			JMenuItem menuitem_save = new JMenuItem ("Save");
			JMenuItem menuitem_quit = new JMenuItem ("Quit");
			
			/*
			menuitem_new.addActionListener(this);
			menuitem_open.addActionListener(this);
			menuitem_save.addActionListener(this);
			menuitem_quit.addActionListener(this);
		*/
			 menu_main.add(menu_file);

		        menu_file.add(menuitem_new);
		    menu_file.add(menuitem_open);
		    menu_file.add(menuitem_save);
		    menu_file.add(menuitem_quit);

		        frame.setJMenuBar(menu_main);
	}
	
	//Popup window to edit groups
	//Displays all edits on the left and the selected group on the right
	public void manageGroup(String groupname) {
		JFrame managerwindow = new JFrame();
		
		JPanel manageoptions = new JPanel(new GridLayout(0, 2));
		manageoptions.setBorder(new TitledBorder ( new EtchedBorder(), "Managing Group: "+ groupname));
		
		JLabel defaultlabel = new JLabel("All Edits");
		JLabel grouplabel = new JLabel(groupname);
		manageoptions.add(defaultlabel);
		manageoptions.add(grouplabel);
		
		JList defaultlist= new JList(defaultgroup.toArray());
		defaultlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane defaultscroll = new JScrollPane(defaultlist);
		
		JList grouplist= new JList(activegroup.toArray());
		grouplist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane groupscroll = new JScrollPane(grouplist);
		
		manageoptions.add(defaultscroll);
		manageoptions.add(groupscroll);
		
		addtogroup = new JButton("Add edit to group");
		remfromgroup = new JButton("Remove edit from group");
		
		manageoptions.add(addtogroup);
		manageoptions.add(remfromgroup);
		
		
		
		managerwindow.add(manageoptions);
		managerwindow.pack();
		managerwindow.setVisible(true);
		
	}
	
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		
	}

	//Functionality for popup windows via creategroup/managegroup
	@Override
	public void actionPerformed(ActionEvent e) {
		String ae = e.getActionCommand();
		if(ae.equals("Create Group")) {
			String groupname = JOptionPane.showInputDialog("Please enter a name for the group");
			//Need to figure out how groups are represented by Jlist
			//Group class might need toString to be displayed
			//testgroups.add(groupname);
			//grouplist.setListData(testgroups.toArray());
		}
		if(ae.equals("Manage Group")) {
			if(grouplist.getSelectedValue() != null) {
				String group = grouplist.getSelectedValue().toString();
				manageGroup(group);
			}
		}
	}
}