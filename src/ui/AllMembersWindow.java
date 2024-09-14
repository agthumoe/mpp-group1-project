package ui;

import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.Auth;
import ui.components.BackToMainMenuButton;
import ui.components.ImmutableTableModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class AllMembersWindow extends MenusWindow {
    private static final long serialVersionUID = -2230098295332595204L;

    private static AllMembersWindow instance;
    private JTable table;
    private JButton btnAddMember, btnBackToMainMenu;
    private DefaultTableModel tableModel;
    private final ControllerInterface controller;

    private AllMembersWindow() {
        this.controller = SystemController.getInstance();
    }

    public synchronized static AllMembersWindow getInstance() {
        if (instance == null) {
            instance = new AllMembersWindow();
        }
        return instance;
    }

    @Override
    public void updateAuth(Auth auth) {
        super.updateAuth(auth);
        if (this.isInitialized()) {
            btnAddMember.setEnabled(Auth.BOTH.equals(auth) || Auth.ADMIN.equals(auth));
        }
    }

    @Override
    public void formatContentPane() {
        this.setTitle("Library Members");
        Font headerFont = new Font("Arial", Font.BOLD, 14);
        getContentPane().setLayout(new BorderLayout(0, 0));
        String[] columnNames = {"Member ID", "First Name", "Last Name", "Street", "City", "State", "Zip", "Telephone"};
        this.tableModel = new ImmutableTableModel(columnNames, 0);
        this.table = new JTable(this.tableModel);
        this.table.setRowHeight(30);
        this.table.getTableHeader().setFont(headerFont);
        this.table.setDefaultRenderer(Object.class, new GradientTableCellRenderer());
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setPreferredSize(new Dimension(tableHeader.getPreferredSize().width, 40));

        JScrollPane scrollPanel = new JScrollPane(table);
        scrollPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        getContentPane().add(scrollPanel);

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.NORTH);
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        this.btnAddMember = new JButton("Add Member");
        this.btnBackToMainMenu = new BackToMainMenuButton();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.add(btnBackToMainMenu);
        panel.add(Box.createHorizontalGlue());
        panel.add(this.btnAddMember);
        this.btnAddMember.addActionListener((e) -> {
            Util.hideAllWindows();
            if (!AddMemberWindow.getInstance().isInitialized()) {
                AddMemberWindow.getInstance().init();
            }
            AddMemberWindow.getInstance().setVisible(true);
            AddMemberWindow.getInstance().reset();
        });
    }

    public void loadData() {
        this.tableModel.setRowCount(0);
        List<LibraryMember> members = this.controller.getAllMembers();
        for (LibraryMember member : members) {
            String[] row = new String[]{member.getMemberId(), member.getFirstName(), member.getLastName(), member.getAddress().getStreet(), member.getAddress().getCity(), member.getAddress().getState(), member.getAddress().getZip(), member.getTelephone()};
            this.tableModel.addRow(row);
        }
    }
}


class GradientTableCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (!isSelected && row % 2 == 0) { // Apply gradient to every other row
            setBackground(new Color(0, 0, 0, 0)); // Transparent background to allow gradient
        } else {
            setBackground(table.getBackground());
        }

        return c;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getBackground().getAlpha() == 0) { // Only paint gradient if background is transparent
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();
            Color startColor = Color.lightGray;
            Color endColor = Color.lightGray;
            GradientPaint gradientPaint = new GradientPaint(0, 0, startColor, 0, height, endColor);
            g2d.setPaint(gradientPaint);
            g2d.fillRect(0, 0, width, height);
        }
        super.paintComponent(g);
    }
}