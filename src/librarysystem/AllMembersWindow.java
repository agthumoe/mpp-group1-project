package librarysystem;

import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;

import javax.swing.*;
import java.awt.*;

import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	public void formatContentPane() {
		this.setTitle("Library Members");
		getContentPane().setLayout(new BorderLayout(0, 0));
		String[] columnNames = { "Member ID", "First Name", "Last Name", "Street", "City", "State", "Zip", "Telephone" };
		this.tableModel = new ImmutableTableModel(columnNames, 0);
		this.table = new JTable(this.tableModel);
		JScrollPane scrollPanel = new JScrollPane(table);
		scrollPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
		getContentPane().add(scrollPanel);

		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
		this.btnAddMember = new JButton("Add Member");
		this.btnBackToMainMenu = new JButton("Back to Main Menu");
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.add(btnBackToMainMenu);
		panel.add(Box.createHorizontalGlue());
		panel.add(this.btnAddMember);
		this.btnAddMember.addActionListener((e) -> {
			Util.hideAllWindows();
			if(!AddMemberWindow.getInstance().isInitialized()) {
				AddMemberWindow.getInstance().init();
			}
			AddMemberWindow.getInstance().setVisible(true);
			AddMemberWindow.getInstance().reset();
		});
	}

	public void loadData() {
		this.tableModel.setRowCount(0);
		List<LibraryMember> members = this.controller.getAllMembers();
		for (LibraryMember member: members) {
			String[] row = new String[]{member.getMemberId(), member.getFirstName(), member.getLastName(), member.getAddress().getStreet(), member.getAddress().getCity(), member.getAddress().getState(), member.getAddress().getZip(), member.getTelephone()};
			this.tableModel.addRow(row);
		}
	}
}
