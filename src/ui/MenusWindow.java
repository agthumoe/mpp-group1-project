package ui;

import business.LibraryMember;
import business.SystemController;
import dataaccess.Auth;

import javax.swing.*;
import java.awt.*;

public abstract class MenusWindow extends JFrame implements LibWindow {
    private boolean isInitialized = false;
    protected JPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu bookMenu, memberMenu, checkoutRecordMenu, accountMenu;
    private JMenuItem loginMenuItem, logoutMenuItem, accountDetailsMenuItem, bookListMenuItem, memberListMenuItem, addMemberMenuItem, addBookMenuItem, checkoutRecordListMenuItem, addCheckoutRecordMenuItem, addAuthorMenuItem, addBookCopiesMenuItem;

    @Override
    public void init() {
        setPreferredSize(new Dimension(800, 600));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formatMenus();
        this.formatContentPane();
        this.pack();
        this.isInitialized = true;
        Util.centerFrameOnDesktop(this);
        this.setVisible(true);
        this.updateAuth(SystemController.getCurrentAuth());
    }

    public void updateAuth(Auth auth) {
        if (this.isInitialized) {
            this.loginMenuItem.setEnabled(false);
            this.logoutMenuItem.setEnabled(true);
            if (Auth.ADMIN.equals(auth)) {
                this.bookMenu.setEnabled(true);
                this.memberMenu.setEnabled(true);
                this.checkoutRecordMenu.setEnabled(false);
                this.addMemberMenuItem.setEnabled(true);
                this.addBookMenuItem.setEnabled(true);
                this.addBookCopiesMenuItem.setEnabled(true);
                this.addAuthorMenuItem.setEnabled(true);
                this.addCheckoutRecordMenuItem.setEnabled(false);
                this.accountDetailsMenuItem.setEnabled(true);
            } else if (Auth.LIBRARIAN.equals(auth)) {
                this.bookMenu.setEnabled(true);
                this.memberMenu.setEnabled(true);
                this.checkoutRecordMenu.setEnabled(true);
                this.addMemberMenuItem.setEnabled(false);
                this.addBookMenuItem.setEnabled(false);
                this.addBookCopiesMenuItem.setEnabled(false);
                this.addAuthorMenuItem.setEnabled(false);
                this.addCheckoutRecordMenuItem.setEnabled(true);
                this.accountDetailsMenuItem.setEnabled(true);
            } else if (Auth.BOTH.equals(auth)) {
                this.bookMenu.setEnabled(true);
                this.memberMenu.setEnabled(true);
                this.checkoutRecordMenu.setEnabled(true);
                this.addMemberMenuItem.setEnabled(true);
                this.addBookMenuItem.setEnabled(true);
                this.addBookCopiesMenuItem.setEnabled(true);
                this.addAuthorMenuItem.setEnabled(true);
                this.addCheckoutRecordMenuItem.setEnabled(true);
                this.accountDetailsMenuItem.setEnabled(true);
            } else if (auth == null) {
                this.loginMenuItem.setEnabled(true);
                this.logoutMenuItem.setEnabled(false);
                this.accountDetailsMenuItem.setEnabled(false);
                this.bookMenu.setEnabled(false);
                this.memberMenu.setEnabled(false);
                this.checkoutRecordMenu.setEnabled(false);
            }
        }
    }

    public abstract void formatContentPane();

    public void formatMenus() {
        this.menuBar = new JMenuBar();
        this.menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
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
        this.addBookCopiesMenuItem = new JMenuItem("Add Copies");
        this.addAuthorMenuItem = new JMenuItem("Add Author");
        this.addMemberMenuItem = new JMenuItem("Add New Member");
        this.checkoutRecordListMenuItem = new JMenuItem("List All Checkout Records");
        this.addCheckoutRecordMenuItem = new JMenuItem("Add Checkout Record");
        this.memberListMenuItem = new JMenuItem("List All Members");
        this.loginMenuItem.addActionListener((e) -> {
            Util.hideAllWindows();
            if (!LoginWindow.getInstance().isInitialized()) {
                LoginWindow.getInstance().init();
                Util.centerFrameOnDesktop(LoginWindow.getInstance());
            }
            LoginWindow.getInstance().setVisible(true);
            LoginWindow.getInstance().reset();
        });
        this.logoutMenuItem.addActionListener((evt) -> {
            SystemController.setCurrentAuth(null);
            JOptionPane.showMessageDialog(this, "Logout Successful");
            // redirect to LibrarySystem windows
            Util.hideAllWindows();
            LibrarySystem.getInstance().setVisible(true);
        });
        this.addAuthorMenuItem.addActionListener((e) -> {
            JDialog dialog = new AddAuthorDialogBox();
            dialog.setVisible(true);
        });
        this.addBookCopiesMenuItem.addActionListener((e) -> {
            JDialog dialog = new AddBookCopyDialogBox();
            dialog.setVisible(true);
        });
        this.bookListMenuItem.addActionListener((e) -> {
            Util.hideAllWindows();
            if (!AllBooksWindow.getInstance().isInitialized()) {
                AllBooksWindow.getInstance().init();
            }
            AllBooksWindow.getInstance().loadData();
            AllBooksWindow.getInstance().setVisible(true);
        });
        this.addBookMenuItem.addActionListener((e) -> {
            Util.hideAllWindows();
            if (!AddBookWindow.getInstance().isInitialized()) {
                AddBookWindow.getInstance().init();
            }
            AddBookWindow.getInstance().setVisible(true);
            AddBookWindow.getInstance().reset();
        });

        this.memberListMenuItem.addActionListener((e) -> {
            Util.hideAllWindows();
            if (!AllMembersWindow.getInstance().isInitialized()) {
                AllMembersWindow.getInstance().init();
            }
            AllMembersWindow.getInstance().loadData();
            AllMembersWindow.getInstance().setVisible(true);
        });
        this.addMemberMenuItem.addActionListener((e) -> {
            Util.hideAllWindows();
            if (!AddMemberWindow.getInstance().isInitialized()) {
                AddMemberWindow.getInstance().init();
            }
            AddMemberWindow.getInstance().setVisible(true);
            AddMemberWindow.getInstance().reset();
        });
        this.bookMenu.add(this.bookListMenuItem);
        this.bookMenu.add(this.addBookMenuItem);
        this.bookMenu.addSeparator();
        this.bookMenu.add(this.addAuthorMenuItem);
        this.bookMenu.add(this.addBookCopiesMenuItem);

        this.addBookMenuItem.addActionListener((e) -> {
            Util.hideAllWindows();
            if (!AddBookWindow.getInstance().isInitialized()) {
                AddBookWindow.getInstance().init();
            }
            AddBookWindow.getInstance().setVisible(true);
            AddBookWindow.getInstance().reset();
        });
        this.addCheckoutRecordMenuItem.addActionListener((e) -> {
            Util.hideAllWindows();
            if (!AddCheckoutsWindow.getInstance().isInitialized()) {
                AddCheckoutsWindow.getInstance().init();
            }
            AddCheckoutsWindow.getInstance().setVisible(true);
        });
        this.checkoutRecordListMenuItem.addActionListener((e) -> {
            Util.hideAllWindows();
            if (!AllCheckoutRecordWindow.getInstance().isInitialized()) {
                AllCheckoutRecordWindow.getInstance().init();
            }
            AllCheckoutRecordWindow.getInstance().loadData();
            AllCheckoutRecordWindow.getInstance().setVisible(true);
        });
        this.accountDetailsMenuItem.addActionListener((e) -> {
            JOptionPane.showMessageDialog(this, "Coming soon");
        });
        this.memberMenu.add(this.memberListMenuItem);
        this.memberMenu.add(this.addMemberMenuItem);

        this.accountMenu.add(this.loginMenuItem);
        this.accountMenu.add(this.accountDetailsMenuItem);
        this.accountMenu.addSeparator();
        this.accountMenu.add(this.logoutMenuItem);

        this.checkoutRecordMenu.add(this.checkoutRecordListMenuItem);
        this.checkoutRecordMenu.add(this.addCheckoutRecordMenuItem);
        this.setJMenuBar(this.menuBar);
    }

    @Override
    public boolean isInitialized() {
        return this.isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        this.isInitialized = val;
    }
}
