package librarysystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.Auth;


public class LibrarySystem extends JFrame implements LibWindow {
    private static LibrarySystem instance = null;
    private final ControllerInterface controller;
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu bookMenu, memberMenu, checkoutRecordMenu, accountMenu;
    private JMenuItem loginMenuItem, logoutMenuItem, accountDetailsMenuItem, bookListMenuItem, memberListMenuItem, addMemberMenuItem, addBookMenuItem, addCheckoutRecordMenuItem;
    private String pathToImage;
	private JLabel label;

    private boolean isInitialized = false;

    private static LibWindow[] allWindows = {
            LibrarySystem.getInstance(),
            LoginWindow.getInstance(),
            AllMemberIdsWindow.getInstance(),
            AllBookIdsWindow.getInstance()
    };

    private LibrarySystem() {
        this.controller = SystemController.getInstance();
    }

    public static void hideAllWindows() {
        for (LibWindow frame : allWindows) {
            frame.setVisible(false);
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
        setPathToImage();
        insertSplashImage();

        createMenus();
        //pack();
        setSize(660, 500);
        isInitialized = true;
    }
    
    private void formatContentPane() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 1));
        getContentPane().add(mainPanel);
    }

    private void setPathToImage() {
        String currDirectory = System.getProperty("user.dir");
        pathToImage = currDirectory + "/src/librarysystem/library.jpg";
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
        this.bookMenu = new JMenu("Book");
		this.memberMenu = new JMenu("Member");
		this.checkoutRecordMenu = new JMenu("Checkout Record");
		this.accountMenu = new JMenu("Account");

        this.menuBar.add(bookMenu);
		this.menuBar.add(memberMenu);
		this.menuBar.add(checkoutRecordMenu);
		this.menuBar.add(accountMenu);

        this.loginMenuItem = new JMenuItem("Login");
		this.logoutMenuItem = new JMenuItem("Logout");
		this.logoutMenuItem.setEnabled(false);
		this.accountDetailsMenuItem = new JMenuItem("Account Details");
        this.bookListMenuItem = new JMenuItem("List All Book");
		this.addBookMenuItem = new JMenuItem("Add New Book");
		this.addMemberMenuItem = new JMenuItem("Add New Member");
		this.addCheckoutRecordMenuItem = new JMenuItem("Add Checkout Record");
		this.memberListMenuItem = new JMenuItem("List All Members");
		this.loginMenuItem.addActionListener(new LoginListener());
		this.logoutMenuItem.addActionListener((evt) -> {
			SystemController.currentAuth = null;
			this.logoutMenuItem.setEnabled(false);
			this.loginMenuItem.setEnabled(true);
			JOptionPane.showMessageDialog(this,"Logout Successful");
		});
		this.bookListMenuItem.addActionListener(new AllBookIdsListener());
        this.memberListMenuItem.addActionListener(new AllMemberIdsListener());
        this.addMemberMenuItem.addActionListener(new AddMemberListener());
        this.addBookMenuItem.addActionListener(new AddBookListener());
        this.bookMenu.add(this.bookListMenuItem);
		this.bookMenu.add(this.addBookMenuItem);

		this.memberMenu.add(this.memberListMenuItem);
		this.memberMenu.add(this.addMemberMenuItem);

		this.accountMenu.add(this.loginMenuItem);
		this.accountMenu.add(this.accountDetailsMenuItem);
		this.accountMenu.add(this.logoutMenuItem);

		this.checkoutRecordMenu.add(this.addCheckoutRecordMenuItem);
    }

    public void updateAuth(Auth auth) {
        this.loginMenuItem.setEnabled(false);
		this.logoutMenuItem.setEnabled(true);
        if (Auth.ADMIN.equals(auth)) {
            this.memberListMenuItem.setEnabled(false);
        } else if (Auth.LIBRARIAN.equals(auth)) {
            this.bookListMenuItem.setEnabled(false);
        } else if (Auth.BOTH.equals(auth)) {

        }
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
            for (String s : ids) {
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
            AllMembersWindow.getInstance().init();
            AllMembersWindow.getInstance().pack();
            AllMembersWindow.getInstance().setVisible(true);

            LibrarySystem.hideAllWindows();
            AllBookIdsWindow.getInstance().init();

            List<String> ids = controller.allMemberIds();
            Collections.sort(ids);
            StringBuilder sb = new StringBuilder();
            for (String s : ids) {
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

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

	class AddMemberListener implements ActionListener {

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

    class AddBookListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            LibrarySystem.hideAllWindows();
            AddBookWindow.getInstance().init();
            AddBookWindow.getInstance().pack();
            AddBookWindow.getInstance().setVisible(true);

            LibrarySystem.hideAllWindows();
            AddBookWindow.getInstance().init();
            AddBookWindow.getInstance().pack();
            AddBookWindow.getInstance().setSize(400,600);
            Util.centerFrameOnDesktop(AddBookWindow.getInstance());
            AddBookWindow.getInstance().setVisible(true);
        }
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;
    }
}
