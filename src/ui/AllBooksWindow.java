package ui;

import business.Book;
import business.ControllerInterface;
import business.SystemController;
import dataaccess.Auth;
import ui.components.BackToMainMenuButton;
import ui.components.ImmutableTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class AllBooksWindow extends MenusWindow {
    private static final long serialVersionUID = -2230098295332595214L;

    private static AllBooksWindow instance;
    private JTable table;
    private JButton btnNewButton, btnBackButton;
    private DefaultTableModel tableModel;
    private final ControllerInterface controller;

    private AllBooksWindow() {
        this.controller = SystemController.getInstance();
    }

    public synchronized static AllBooksWindow getInstance() {
        if (instance == null) {
            instance = new AllBooksWindow();
        }
        return instance;
    }

    @Override
    public void updateAuth(Auth auth) {
        super.updateAuth(auth);
        if (this.isInitialized()) {
            this.btnNewButton.setEnabled(Auth.BOTH.equals(auth) || Auth.ADMIN.equals(auth));
        }
    }

    @Override
    public void formatContentPane() {
        this.setTitle("Library Members");
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        getContentPane().setLayout(new BorderLayout(0, 0));
        String[] columnNames = {"ISBN", "Title", "Max Check-out Length", "Authors", "Num of Copies", ""};
        this.tableModel = new ImmutableTableModel(columnNames, 0);
        this.table = new JTable(this.tableModel);
        this.table.setRowHeight(30);
        this.table.getTableHeader().setFont(headerFont);
        this.table.setDefaultRenderer(Object.class, new GradientTableCellRenderer());
        JScrollPane scrollPanel = new JScrollPane(table);
        scrollPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(scrollPanel);
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 40));
        table.setSelectionBackground(Color.red);
        table.setSelectionForeground(Color.red);

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.NORTH);
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        btnNewButton = new JButton("Add Book");
        btnBackButton = new BackToMainMenuButton();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(btnBackButton);
        panel.add(Box.createHorizontalGlue());
        panel.add(btnNewButton);

        btnNewButton.addActionListener(e -> {
            Util.hideAllWindows();
            if (!AddBookWindow.getInstance().isInitialized()) {
                AddBookWindow.getInstance().init();
            }
            AddBookWindow.getInstance().setVisible(true);
            AddBookWindow.getInstance().reset();
        });
    }

    public void loadData() {
        this.tableModel.setRowCount(0);
        List<Book> books = this.controller.getAllBooks();
        for (Book book : books) {
            String[] row = new String[]{book.getIsbn(), book.getTitle(), String.valueOf(book.getMaxCheckoutLength()), !book.getAuthors().isEmpty() ? book.getAuthors().stream().map((a) -> a.getFirstName() + " " + a.getLastName()).collect(Collectors.joining(", ")) : "", String.valueOf(book.getNumCopies()), "Add Copy"};
            this.tableModel.addRow(row);
        }
    }
}
