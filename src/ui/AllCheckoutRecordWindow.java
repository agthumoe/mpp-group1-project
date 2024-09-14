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
        getContentPane().setLayout(new BorderLayout(0, 0));
        String[] columnNames = {"Record ID", "Member ID", "Member Name", "Date of Checkout", "Due Date", "Book Title", "Copy Number"};
        this.tableModel = new ImmutableTableModel(columnNames, 0);
        this.table = new JTable(this.tableModel);
        JScrollPane scrollPanel = new JScrollPane(table);
        scrollPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(scrollPanel);

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
                String[] row = new String[]{record.getRecordID(), record.getMember().getMemberId(), record.getMember().getFirstName() + " " + record.getMember().getLastName(),
                        entry.getDateOfCheckout().format(df), entry.getDueDate().format(df),
                        entry.getBookCopy().getBook().getTitle(), Integer.toString(entry.getBookCopy().getCopyNum())};
                this.tableModel.addRow(row);
            }
        }
    }
}
