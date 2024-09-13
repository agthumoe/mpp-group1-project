package librarysystem;

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
import java.lang.reflect.Member;
import java.util.List;

public class AllMembersWindow extends MenusWindow {
	private static final long serialVersionUID = -2230098295332595204L;

	private static AllMembersWindow instance;
	private JTable table;
	private JButton btnNewButton;
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
	public void formatContentPane() {
		this.setTitle("Library Members");
		getContentPane().setLayout(new BorderLayout(0, 0));
		String[] columnNames = { "Member ID", "First Name", "Last Name", "Street", "City", "State", "Zip", "Telephone" };
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

		btnNewButton = new JButton("Add Member");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 1;
		panel.add(btnNewButton, gbc_btnNewButton);

		this.loadData();
	}

	private void loadData() {
		List<LibraryMember> members = this.controller.getAllMembers();
		for (LibraryMember member: members) {
			String[] row = new String[]{member.getMemberId(), member.getFirstName(), member.getLastName(), member.getAddress().getStreet(), member.getAddress().getCity(), member.getAddress().getState(), member.getAddress().getZip(), member.getTelephone()};
			this.tableModel.addRow(row);
		}
	}
}
