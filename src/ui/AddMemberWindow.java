package ui;

import business.Address;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import exceptions.ValidationException;
import ui.components.CustomPanel;

import javax.swing.*;
import java.awt.*;

public class AddMemberWindow extends MenusWindow {
    private static AddMemberWindow instance;
    private final ControllerInterface controller;
    private JTextField memberIdField, firstNameField, lastNameField, streetField, cityField, stateField, zipField, telephoneField;
    private JButton addButton, backButton;

    private AddMemberWindow() {
        this.controller = SystemController.getInstance();
    }

    public synchronized static AddMemberWindow getInstance() {
        if (instance == null) {
            instance = new AddMemberWindow();
        }
        return instance;
    }

    @Override
    public void formatContentPane() {
        setTitle("Add Library Member");
        CustomPanel panel = new CustomPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Arial", Font.BOLD, 12);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 12);


        JLabel memberIdLabel = new JLabel("Member ID:");
        memberIdLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(memberIdLabel, gbc);

        memberIdField = new JTextField(20);
        memberIdField.setFont(textFieldFont);
        gbc.gridx = 1;
        gbc.gridy = 0;
        memberIdField.setEditable(false);
        panel.add(memberIdField, gbc);

        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(firstNameLabel, gbc);

        firstNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(firstNameField, gbc);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lastNameLabel, gbc);


        lastNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(lastNameField, gbc);

        JLabel streetLabel = new JLabel("Street:");
        streetLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(streetLabel, gbc);

        streetField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(streetField, gbc);

        JLabel cityLabel = new JLabel("City:");
        cityLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(cityLabel, gbc);

        cityField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(cityField, gbc);

        JLabel stateLabel = new JLabel("State:");
        stateLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(stateLabel, gbc);

        stateField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(stateField, gbc);

        JLabel zipLabel = new JLabel("Zip:");
        zipLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(zipLabel, gbc);

        zipField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(zipField, gbc);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(phoneLabel, gbc);

        telephoneField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(telephoneField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        addButton = new JButton("Add Member");
        buttonPanel.add(addButton);

        backButton = new JButton("Back to Main");
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

        add(panel);

        addButton.addActionListener(e -> {
            try {
                String firstName = Util.isRequired(firstNameField.getText(), "First name");
                String lastName = Util.isRequired(lastNameField.getText(), "Last name");
                String street = streetField.getText();
                String city = cityField.getText();
                String state = stateField.getText();
                String zip = Util.isNumericString(zipField.getText(), 5, 5, "Zip code");
                String telephone = Util.isRequired(telephoneField.getText(), "Telephone");

                String id = Util.getRandom();
                controller.addMember(new LibraryMember(id, firstName, lastName, telephone, new Address(street, city, state, zip)));
                JOptionPane.showMessageDialog(AddMemberWindow.this, "New Member ID: " + id + " added successfully.");
                this.reset();
            } catch (ValidationException exception) {
                JOptionPane.showMessageDialog(AddMemberWindow.this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            Util.hideAllWindows();
            LibrarySystem.getInstance().setVisible(true);
            this.setVisible(false);
        });
    }

    public void reset() {
        this.memberIdField.setText("");
        this.firstNameField.setText("");
        this.lastNameField.setText("");
        this.streetField.setText("");
        this.cityField.setText("");
        this.stateField.setText("");
        this.zipField.setText("");
        this.telephoneField.setText("");
    }
}
