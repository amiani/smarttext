package main;


import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;

import java.util.LinkedList;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * UserInterface
 * Threaded class which runs the SmartText ui
 * instantiates listeners and managers for program function
 *
 */
public final class UserInterface extends JFrame implements Runnable, ActionListener, ListSelectionListener{
	private Thread t;
	
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
	private static JList defaulteditlist;
	private static JList groupcontentlist;
	
	private static JTextArea area;
	private static JFrame frame;
	private static int returnValue = 0;
	
	private EditManager em;
	private GroupManager gm;
	private EditListener editlisten;
	private UIListener listener;
	private int[] selectededits;
	//Default group + active group are only really here for testing. Will be replaced by edit groups on implementation
	private static LinkedList<String> defaultgroup;
	private static LinkedList<String> activegroup;
	
	
	
	public UserInterface() { }
	
	
	//Methods to update list selections
	//Necessary for UIListener functionality
	private void updateListener() {
		listener.setEdits(editlist.getSelectedIndices());
		listener.setAddEdits(defaulteditlist.getSelectedIndices());
		listener.setRemovedEdits(groupcontentlist.getSelectedIndices());
	}

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
	    area = new JTextArea();
		em = new EditManager();
		gm = new GroupManager();
		
		//Creation of default edit group
		gm.createGroup();
		Listener.setDefaultList(em.edits());
		Listener.setActiveList(Listener.getDefaultList());
		
		editlist= new JList(Listener.getActiveList().toArray());
		grouplist = new JList(gm.getGroups().toArray());
	    defaulteditlist = new JList();
	    groupcontentlist = new JList();
		
	    defaulteditlist.setListData(Listener.getDefaultList().toArray());
		defaulteditlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    
	    
		listener = new UIListener(em, gm, new int[] {}, area, editlist, grouplist, groupcontentlist);
	    editlisten = new EditListener(em, gm, editlist);
	    
	    
	    //Creation of the text field
	    textarea = new JPanel(new BorderLayout());
	    textarea.setBorder(new TitledBorder ( new EtchedBorder(), "Text Area"));
		area.getDocument().addDocumentListener(editlisten);
	//area.getDocument().addDocumentListener(this);
		JScrollPane textscroll = new JScrollPane();
		textscroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		textscroll.setViewportView(area);
		
		
		//Creation of edit sidebar
		editarea = new JPanel(new BorderLayout());
		editarea.setBorder(new TitledBorder ( new EtchedBorder(), "Edit Area"));
		
		//Creation of list area for edits/groups
		JLabel groupLabel = new JLabel("Edit Group:");
		JLabel editLabel = new JLabel("Edits:");
		editlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		editscroll = new JScrollPane();
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
		
		
		//Creation of buttons and attaching actionlisteners
		editoptions = new JPanel(new GridLayout(0,2));
		editoptions.setBorder(new TitledBorder ( new EtchedBorder(), "Edit Options"));
		undobutton = new JButton("Undo");
		undobutton.addActionListener(listener.new HandleUndoAction(editlist.getSelectedIndices()));
		groupbutton = new JButton("Create Group");
		groupbutton.addActionListener(listener.new HandleCreateGroupAction());
		delbutton = new JButton("Delete Edit");
		delbutton.addActionListener(listener.new HandleDeleteEditsAction(editlist.getSelectedIndices()));
		managebutton = new JButton("Manage Group");
		deletegroup = new JButton("Delete Group");
		deletegroup.addActionListener(listener.new HandleDeleteGroupAction(grouplist.getSelectedIndex()));
		managebutton.addActionListener(this);
		editoptions.add(undobutton);
		editoptions.add(delbutton);
		editoptions.add(groupbutton);
		editoptions.add(managebutton);
		editoptions.add(deletegroup);
		
	
		//Need to add editoptions below the JList components
		editarea.add(editoptions, BorderLayout.SOUTH);
		
		JLabel instr = new JLabel("Shift or Ctrl Click to select multiple edits");
		editarea.add(instr, BorderLayout.NORTH);
	
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
			
	
			 menu_main.add(menu_file);

		        menu_file.add(menuitem_new);
		    menu_file.add(menuitem_open);
		    menu_file.add(menuitem_save);
		    menu_file.add(menuitem_quit);

	
		    frame.setJMenuBar(menu_main);
		   
	while(true) {
		//Polls JList objects
		updateListener();
		
	}

	}
	

	//Popup window to edit groups
	//Displays all edits on the left and the selected group on the right
	public void manageGroup(String groupname) {
		if(grouplist.getSelectedIndex() != 0) {
		JFrame managerwindow = new JFrame();
		
		JPanel manageoptions = new JPanel(new GridLayout(0, 2));
		manageoptions.setBorder(new TitledBorder ( new EtchedBorder(), "Managing Group: "+ groupname));
		
		JLabel defaultlabel = new JLabel("All Edits");
		JLabel grouplabel = new JLabel(groupname);
		manageoptions.add(defaultlabel);
		manageoptions.add(grouplabel);
		
		
		defaulteditlist.setListData(Listener.getDefaultList().toArray());
		defaulteditlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane defaultscroll = new JScrollPane(defaulteditlist);
		
		
		groupcontentlist.setListData(Listener.getActiveList().toArray());
		groupcontentlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane groupscroll = new JScrollPane(groupcontentlist);
		
		manageoptions.add(defaultscroll);
		manageoptions.add(groupscroll);
		
		addtogroup = new JButton("Add edit to group");
		addtogroup.addActionListener(listener.new HandleAddEditAction(defaulteditlist.getSelectedIndices(), grouplist.getSelectedIndex()));
		remfromgroup = new JButton("Remove edit from group");
		remfromgroup.addActionListener((listener.new HandleRemoveEditAction(grouplist.getSelectedIndex(), groupcontentlist.getSelectedIndices())));
		
		manageoptions.add(addtogroup);
		manageoptions.add(remfromgroup);
		
		
		
		managerwindow.add(manageoptions);
		managerwindow.pack();
		managerwindow.setVisible(true);
		}
		else {
			System.out.println("Cannot manage default group");
		}
		
	}
	
	public void start() {
		System.out.println("Starting UIThread");
		if(t == null) {
			t = new Thread(this, "uithread");
			t.start();
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
		if(grouplist.getSelectedIndex() == 0 || grouplist.getSelectedIndex() == -1) {
			Listener.setActiveList(Listener.getDefaultList());
			editlist.setListData(Listener.getActiveList().toArray());
		}
		else {
			try {
			int[] editIds = gm.getEditList(grouplist.getSelectedIndex()).stream()
					.mapToInt(Integer::intValue).toArray();
			Listener.setActiveList(em.edits(editIds));
			if(Listener.getActiveList() != null) {
				editlist.setListData(Listener.getActiveList().toArray());
			}
			}catch(NullPointerException y) {
				
			}
		}
	}

	
	//Functionality for popup windows via creategroup/managegroup
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String ae = e.getActionCommand();
	
		if(ae.equals("Manage Group")) {
			if(grouplist.getSelectedValue() != null) {
				String group = grouplist.getSelectedValue().toString();
				manageGroup(group);
			}
		}
	}
	

}