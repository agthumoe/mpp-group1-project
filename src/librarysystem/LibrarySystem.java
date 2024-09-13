package librarysystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import business.ControllerInterface;
import business.SystemController;


public class LibrarySystem extends JFrame implements LibWindow {
	private static LibrarySystem instance = null;
	private final ControllerInterface controller;
	private JPanel mainPanel;
	private JMenuBar menuBar;
    private JMenu options;
    private JMenuItem login, allBookIds, allMemberIds, addMember;
    private String pathToImage;
	private JLabel label;

    private boolean isInitialized = false;
    
    private static LibWindow[] allWindows = {
    	LibrarySystem.getInstance(),
		LoginWindow.getInstance(),
		AllMemberIdsWindow.getInstance(),
		AllBookIdsWindow.getInstance(),
		AddMemberWindow.getInstance()
	};

	private LibrarySystem() {
		this.controller = SystemController.getInstance();
	}

	public static void hideAllWindows() {
		int id = 0;
		for(LibWindow frame: allWindows) {
			if (frame == null) {
				System.out.println("fram is being null: " + id);
			}
				frame.setVisible(false);
			id++;
		}
	}

	public synchronized static LibrarySystem getInstance() {
		if (instance == null) {
			instance = new LibrarySystem();
		}
		return instance;
	}
    
    public void init() {
    	formatContentPane();
//    	setPathToImage();
//    	insertSplashImage();
		
		createMenus();
		test();
		//pack();
		setSize(800,600);
		isInitialized = true;
    }

	private void test() {
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
//		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));


		JPanel page1 = new JPanel();
		page1.add(new JLabel("This is Tab 1"));

		// Create the second tab (page2) and add a JLabel to it
		JPanel page2 = new JPanel();
		page2.add(new JLabel("This is Tab 2"));

		// Create the third tab (page3) and add a JLabel to it
		JPanel page3 = new JPanel();
		page3.add(new JLabel("This is Tab 3"));

		JPanel page4 = new JPanel();
		page4.add(new JLabel("This is Tab 4"));

		JPanel page5 = new JPanel();
		page5.add(new JLabel("This is Tab 5"));

		JPanel page6 = new JPanel();
		page6.add(new JLabel("This is Tab 6"));

		JPanel page7 = new JPanel();
		page7.add(new JLabel("This is Tab 7"));

		JPanel page8 = new JPanel();
		page8.add(new JLabel("This is Tab 8"));

		// Add the three tabs to the JTabbedPane
		tabbedPane.addTab("Add Member", page1);
		tabbedPane.addTab("Add Book", page2);
		tabbedPane.addTab("Tab 3", page3);
		tabbedPane.addTab("Tab 4", page4);
		tabbedPane.addTab("Tab 5", page5);
		tabbedPane.addTab("Tab 6", page6);
		tabbedPane.addTab("Tab 7", page7);
		tabbedPane.addTab("Tab 8", page8);

//		this.label = new JLabel("Hello World 2");
//		this.label.setBounds(247, 0, 305, 16);
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.anchor = GridBagConstraints.WEST;
//		gbc.insets = new Insets(0, 0, 0, 0);
//		gbc.gridx = 0;
//		gbc.gridy = 0;
//		mainPanel.add(label, gbc);
	}
    
    private void formatContentPane() {
		mainPanel = new JPanel();
//		mainPanel.setLayout(new GridLayout(1,1));
		getContentPane().add(mainPanel);	
	}
    
    private void setPathToImage() {
    	String currDirectory = System.getProperty("user.dir");
    	pathToImage = currDirectory+"/src/librarysystem/library.jpg";
    }
    
    private void insertSplashImage() {
        ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));	
    }

    private void createMenus() {
    	menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems();
		setJMenuBar(menuBar);		
    }
    
    private void addMenuItems() {
       options = new JMenu("Options");  
 	   menuBar.add(options);
 	   login = new JMenuItem("Login");
 	   login.addActionListener(new LoginListener());
 	   allBookIds = new JMenuItem("All Book Ids");
 	   allBookIds.addActionListener(new AllBookIdsListener());
 	   allMemberIds = new JMenuItem("All Member Ids");
 	   allMemberIds.addActionListener(new AllMemberIdsListener());
		addMember = new JMenuItem("Add Member");
		addMember.addActionListener(new AddMemberIdsListener());
 	   options.add(login);
 	   options.add(allBookIds);
 	   options.add(allMemberIds);
		options.add(addMember);
    }
    
    class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginWindow.getInstance().init();
			Util.centerFrameOnDesktop(LoginWindow.getInstance());
			LoginWindow.getInstance().setVisible(true);
			
		}
    	
    }
    class AllBookIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.getInstance().init();
			
			List<String> ids = controller.allBookIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for(String s: ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllBookIdsWindow.getInstance().setData(sb.toString());
			AllBookIdsWindow.getInstance().pack();
			//AllBookIdsWindow.getInstance().setSize(660,500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.getInstance());
			AllBookIdsWindow.getInstance().setVisible(true);
			
		}
    	
    }
    
    class AllMemberIdsListener implements ActionListener {

    	@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllMemberIdsWindow.getInstance().init();
			AllMemberIdsWindow.getInstance().pack();
			AllMemberIdsWindow.getInstance().setVisible(true);
			
			
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.getInstance().init();
			
			List<String> ids = controller.allMemberIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for(String s: ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllMemberIdsWindow.getInstance().setData(sb.toString());
			AllMemberIdsWindow.getInstance().pack();
			//AllMemberIdsWindow.getInstance().setSize(660,500);
			Util.centerFrameOnDesktop(AllMemberIdsWindow.getInstance());
			AllMemberIdsWindow.getInstance().setVisible(true);
			
			
		}
    	
    }

	class AddMemberIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddMemberWindow.getInstance().init();
			AddMemberWindow.getInstance().pack();
			AddMemberWindow.getInstance().setVisible(true);


			LibrarySystem.hideAllWindows();
			AddMemberWindow.getInstance().init();

//			List<String> ids = controller.allMemberIds();
//			Collections.sort(ids);
//			StringBuilder sb = new StringBuilder();
//			for(String s: ids) {
//				sb.append(s + "\n");
//			}
//			System.out.println(sb.toString());
//			AllMemberIdsWindow.getInstance().setData(sb.toString());
			AddMemberWindow.getInstance().pack();
			AddMemberWindow.getInstance().setSize(400,600);
			Util.centerFrameOnDesktop(AddMemberWindow.getInstance());
			AddMemberWindow.getInstance().setVisible(true);


		}

	}

	@Override
	public boolean isInitialized() {
		return isInitialized;
	}


	@Override
	public void isInitialized(boolean val) {
		isInitialized =val;
		
	}
    
}
