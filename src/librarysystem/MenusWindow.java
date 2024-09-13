package librarysystem;

import business.SystemController;

import javax.swing.*;
import java.awt.*;

public abstract class MenusWindow extends JFrame implements LibWindow {
    private boolean isInitialized = false;
    protected JPanel mainPanel;
    private JMenuBar menuBar;
    private JMenu bookMenu, memberMenu, checkoutRecordMenu, accountMenu;
    private JMenuItem loginMenuItem, logoutMenuItem, accountDetailsMenuItem, bookListMenuItem, memberListMenuItem, addMemberMenuItem, addBookMenuItem, addCheckoutRecordMenuItem;

    @Override
    public void init() {
        setSize(660, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.formatMenus();
        this.formatContentPane();
        this.pack();
        this.isInitialized = true;
        this.centerFrameOnDesktop();
        this.setVisible(true);
    }

    private final LibWindow[] allWindows = {
            LibrarySystem.getInstance(),
            LoginWindow.getInstance(),
            AllMemberIdsWindow.getInstance(),
            AllBookIdsWindow.getInstance()
    };

    protected final void hideAllWindows() {
        for (LibWindow frame : this.allWindows) {
            frame.setVisible(false);
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
        this.addMemberMenuItem = new JMenuItem("Add New Member");
        this.addCheckoutRecordMenuItem = new JMenuItem("Add Checkout Record");
        this.memberListMenuItem = new JMenuItem("List All Members");
        this.loginMenuItem.addActionListener((e) -> {
            LibrarySystem.hideAllWindows();
            LoginWindow.getInstance().init();
        });
        this.logoutMenuItem.addActionListener((evt) -> {
            SystemController.currentAuth = null;
            this.logoutMenuItem.setEnabled(false);
            this.loginMenuItem.setEnabled(true);
            JOptionPane.showMessageDialog(this,"Logout Successful");
        });
        this.bookListMenuItem.addActionListener((e) -> {
            // implement here.
        });
        this.memberListMenuItem.addActionListener((e) -> {
            // implement here.
        });
        this.addMemberMenuItem.addActionListener((e) -> {
            // implement here.
        });
        this.bookMenu.add(this.bookListMenuItem);
        this.bookMenu.add(this.addBookMenuItem);

        this.memberMenu.add(this.memberListMenuItem);
        this.memberMenu.add(this.addMemberMenuItem);

        this.accountMenu.add(this.loginMenuItem);
        this.accountMenu.add(this.accountDetailsMenuItem);
        this.accountMenu.add(this.logoutMenuItem);

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

    private void centerFrameOnDesktop() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = this.getSize().height;
        int frameWidth = this.getSize().width;
        this.setLocation((width - frameWidth) / 2, (height - frameHeight) / 2);
    }
}
