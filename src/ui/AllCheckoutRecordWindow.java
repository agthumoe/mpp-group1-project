package ui;

import business.CheckoutRecord;
import business.ControllerInterface;
import business.RecordEntry;
import business.SystemController;
import dataaccess.Auth;
import ui.components.BackToMainMenuButton;
import ui.components.ImmutableTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AllCheckoutRecordWindow extends MenusWindow {
    private static AllCheckoutRecordWindow instance;
    private JTable table;
    private JButton btnNewButton, btnBackButton;
    private DefaultTableModel tableModel;
    private final ControllerInterface controller;

    private AllCheckoutRecordWindow() {
        this.controller = SystemController.getInstance();
    }

    public synchronized static AllCheckoutRecordWindow getInstance() {
        if (instance == null) {
            instance = new AllCheckoutRecordWindow();
        }
        return instance;
    }

    @Override
    public void updateAuth(Auth auth) {
        super.updateAuth(auth);
        if (this.isInitialized()) {
            this.btnNewButton.setEnabled(Auth.BOTH.equals(auth) || Auth.LIBRARIAN.equals(auth));
        }
    }

    @Override
    public void formatContentPane() {
        this.setTitle("Checkout Records");
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        getContentPane().setLayout(new BorderLayout(0, 0));
        String[] columnNames = {"ISBN", "Book Title", "Member ID", "Member Name", "Date of Checkout", "Due Date", "Copy Number"};
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
        btnNewButton = new JButton("Add New Record");
        btnBackButton = new BackToMainMenuButton();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(btnBackButton);
        panel.add(Box.createHorizontalGlue());
        panel.add(btnNewButton);

        btnNewButton.addActionListener(e -> {
            Util.hideAllWindows();
            if (!AddCheckoutsWindow.getInstance().isInitialized()) {
                AddCheckoutsWindow.getInstance().init();
            }
            AddCheckoutsWindow.getInstance().setVisible(true);
            AddCheckoutsWindow.getInstance().reset();
        });
    }

    public void loadData() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.tableModel.setRowCount(0);

        List<CheckoutRecord> records = this.controller.getAllCheckoutRecords();
        for (CheckoutRecord record : records) {
            for (RecordEntry entry : record.getEntries()) {
                String[] row = new String[]{entry.getBookCopy().getBook().getIsbn(), entry.getBookCopy().getBook().getTitle(),
                        record.getMember().getMemberId(), record.getMember().getFirstName() + " " + record.getMember().getLastName(),
                        entry.getDateOfCheckout().format(df), entry.getDueDate().format(df),
                        Integer.toString(entry.getBookCopy().getCopyNum())};
                this.tableModel.addRow(row);
            }
        }
    }
}
