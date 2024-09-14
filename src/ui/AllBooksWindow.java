package ui;

import business.Book;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JScrollPane;
import java.awt.GridBagConstraints;
import javax.swing.JTable;
import java.awt.Insets;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.lang.reflect.Member;
import java.util.List;

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
    public void formatContentPane() {
        this.setTitle("Library Members");
        getContentPane().setLayout(new BorderLayout(0, 0));
        String[] columnNames = { "ISBN", "Title", "Max Check-out Length", "Author First Name", "Author Last Name", "Num of Copies"};
        this.tableModel = new ImmutableTableModel(columnNames, 0);
        this.table = new JTable(this.tableModel);
        JScrollPane scrollPanel = new JScrollPane(table);
        scrollPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(scrollPanel);

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.NORTH);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] {10, 487, 0, 10};
        gbl_panel.rowHeights = new int[]{10, 23, 0};
        gbl_panel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        btnBackButton = new JButton("Back to Main");
        GridBagConstraints gbc_btnBackButton = new GridBagConstraints();
        gbc_btnBackButton.fill = GridBagConstraints.WEST;
        gbc_btnBackButton.gridx = 0;
        gbc_btnBackButton.gridy = 0;
        panel.add(btnBackButton, gbc_btnBackButton);

        btnBackButton.addActionListener(e -> {
            Util.hideAllWindows();
            LibrarySystem.getInstance().setVisible(true);
            this.setVisible(false);
        });

        btnNewButton = new JButton("Add Book");
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton.fill = GridBagConstraints.EAST;
        gbc_btnNewButton.gridx = 1;
        gbc_btnNewButton.gridy = 0;
        panel.add(btnNewButton, gbc_btnNewButton);

        btnNewButton.addActionListener(e -> {
            Util.hideAllWindows();
            LibrarySystem.getInstance().setVisible(true);
            this.setVisible(false);

            Util.hideAllWindows();
            AddBookWindow.getInstance().init();
            AddBookWindow.getInstance().pack();
            AddBookWindow.getInstance().setVisible(true);

            Util.hideAllWindows();
            AddBookWindow.getInstance().init();
            AddBookWindow.getInstance().pack();
            AddBookWindow.getInstance().setSize(400,600);
            Util.centerFrameOnDesktop(AddBookWindow.getInstance());
            AddBookWindow.getInstance().setVisible(true);

        });

        this.loadData();
    }

    private void loadData() {
        List<Book> books = this.controller.getAllBooks();

        for (Book book: books) {
            String[] row = new String[]{book.getIsbn(), book.getTitle(), String.valueOf(book.getMaxCheckoutLength()), !book.getAuthors().isEmpty() ? book.getAuthors().getFirst().getFirstName() :"", !book.getAuthors().isEmpty() ? book.getAuthors().getFirst().getLastName() :"", String.valueOf(book.getNumCopies())};
            this.tableModel.addRow(row);
        }
    }
}
