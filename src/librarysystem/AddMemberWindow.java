package librarysystem;
import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.UUID;

public class AddMemberWindow extends JFrame implements LibWindow {

//    private static final long serialVersionUID = 1L;
    private static AddMemberWindow instance;
    private boolean isInitialized = false;
    private final ControllerInterface controller;

    private JTextField memberIdField, firstNameField, lastNameField, streetField, cityField, stateField, zipField, telephoneField;
    private JButton addButton, backButton;
//    private JLabel statusLabel;

    private AddMemberWindow() {
        this.controller = SystemController.getInstance();
    }

    public synchronized static AddMemberWindow getInstance() {
        if (instance == null) {
            instance = new AddMemberWindow();
        }
        return instance;
    }

    public void init() {
        setTitle("Add Library Member");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel memberIdLabel = new JLabel("Member ID:");
        memberIdLabel.setBounds(10, 20, 80, 25);
        panel.add(memberIdLabel);

        memberIdField = new JTextField(20);
        memberIdField.setBounds(100, 20, 165, 25);
        memberIdField.setEditable(false);
        panel.add(memberIdField);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setBounds(10, 60, 80, 25);
        panel.add(firstNameLabel);

        firstNameField = new JTextField(20);
        firstNameField.setBounds(100, 60, 165, 25);
        panel.add(firstNameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setBounds(10, 100, 80, 25);
        panel.add(lastNameLabel);

        lastNameField = new JTextField(20);
        lastNameField.setBounds(100, 100, 165, 25);
        panel.add(lastNameField);

        JLabel streetLabel = new JLabel("Street:");
        streetLabel.setBounds(10, 140, 80, 25);
        panel.add(streetLabel);

        streetField = new JTextField(20);
        streetField.setBounds(100, 140, 165, 25);
        panel.add(streetField);

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setBounds(10, 180, 80, 25);
        panel.add(cityLabel);

        cityField = new JTextField(20);
        cityField.setBounds(100, 180, 165, 25);
        panel.add(cityField);

        JLabel stateLabel = new JLabel("State:");
        stateLabel.setBounds(10, 220, 80, 25);
        panel.add(stateLabel);

        stateField = new JTextField(20);
        stateField.setBounds(100, 220, 165, 25);
        panel.add(stateField);

        JLabel zipLabel = new JLabel("Zip:");
        zipLabel.setBounds(10, 260, 80, 25);
        panel.add(zipLabel);

        zipField = new JTextField(20);
        zipField.setBounds(100, 260, 165, 25);
        panel.add(zipField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(10, 300, 80, 25);
        panel.add(phoneLabel);

        telephoneField = new JTextField(20);
        telephoneField.setBounds(100, 300, 165, 25);
        panel.add(telephoneField);

        addButton = new JButton("Add Member");
        addButton.setBounds(100, 340, 150, 25);
        panel.add(addButton);

        backButton = new JButton("Back to Main");
        backButton.setBounds(100, 380, 150, 25);
        panel.add(backButton);

//        statusLabel = new JLabel("");
//        statusLabel.setBounds(10, 420, 300, 25);
//        panel.add(statusLabel);

        add(panel);

        addButton.addActionListener(e -> {
            String memberId = memberIdField.getText();
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String street = streetField.getText();
            String city = cityField.getText();
            String state = stateField.getText();
            String zip = zipField.getText();
            String telephone = telephoneField.getText();

            controller.addMember(new LibraryMember(UUID.randomUUID().toString(), firstName, lastName, telephone, new Address(street, city, state, zip)));
            JOptionPane.showMessageDialog(AddMemberWindow.this, "Member added successfully.");
            this.reset();
        });

        backButton.addActionListener(e -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.getInstance().setVisible(true);
            this.setVisible(false);
        });

        isInitialized = true;
    }

    private void reset() {
        this.memberIdField.setText("");
        this.firstNameField.setText("");
        this.lastNameField.setText("");
        this.streetField.setText("");
        this.cityField.setText("");
        this.stateField.setText("");
        this.zipField.setText("");
        this.telephoneField.setText("");
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;
    }
}
