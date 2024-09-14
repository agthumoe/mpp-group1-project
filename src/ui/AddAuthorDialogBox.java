package ui;

import business.Address;
import business.Author;
import business.ControllerInterface;
import business.SystemController;
import exceptions.ValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAuthorDialogBox extends JDialog {
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField telephoneField;
    private JLabel lblStreet;
    private JLabel lblCity;
    private JLabel lblState;
    private JLabel lblZip;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField stateField;
    private JTextField zipField;
    private JLabel lblBio;
    private JButton btnAdd;
    private JTextArea bioField;

    public AddAuthorDialogBox() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        JLabel lalFirstName = new JLabel("First Name");
        GridBagConstraints gbc_lalFirstName = new GridBagConstraints();
        gbc_lalFirstName.insets = new Insets(0, 0, 5, 5);
        gbc_lalFirstName.anchor = GridBagConstraints.EAST;
        gbc_lalFirstName.gridx = 0;
        gbc_lalFirstName.gridy = 0;
        getContentPane().add(lalFirstName, gbc_lalFirstName);

        firstNameField = new JTextField();
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(0, 0, 5, 0);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 1;
        gbc_textField.gridy = 0;
        getContentPane().add(firstNameField, gbc_textField);
        firstNameField.setColumns(10);

        JLabel lblLastName = new JLabel("Last Name");
        GridBagConstraints gbc_lblLastName = new GridBagConstraints();
        gbc_lblLastName.anchor = GridBagConstraints.EAST;
        gbc_lblLastName.insets = new Insets(0, 0, 5, 5);
        gbc_lblLastName.gridx = 0;
        gbc_lblLastName.gridy = 1;
        getContentPane().add(lblLastName, gbc_lblLastName);

        lastNameField = new JTextField();
        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        gbc_textField_1.insets = new Insets(0, 0, 5, 0);
        gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_1.gridx = 1;
        gbc_textField_1.gridy = 1;
        getContentPane().add(lastNameField, gbc_textField_1);
        lastNameField.setColumns(10);

        JLabel lblTelephone = new JLabel("Telephone");
        GridBagConstraints gbc_lblTelephone = new GridBagConstraints();
        gbc_lblTelephone.anchor = GridBagConstraints.EAST;
        gbc_lblTelephone.insets = new Insets(0, 0, 5, 5);
        gbc_lblTelephone.gridx = 0;
        gbc_lblTelephone.gridy = 2;
        getContentPane().add(lblTelephone, gbc_lblTelephone);

        telephoneField = new JTextField();
        GridBagConstraints gbc_textField_2 = new GridBagConstraints();
        gbc_textField_2.insets = new Insets(0, 0, 5, 0);
        gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_2.gridx = 1;
        gbc_textField_2.gridy = 2;
        getContentPane().add(telephoneField, gbc_textField_2);
        telephoneField.setColumns(10);

        lblStreet = new JLabel("Street");
        GridBagConstraints gbc_lblStreet = new GridBagConstraints();
        gbc_lblStreet.anchor = GridBagConstraints.EAST;
        gbc_lblStreet.insets = new Insets(0, 0, 5, 5);
        gbc_lblStreet.gridx = 0;
        gbc_lblStreet.gridy = 3;
        getContentPane().add(lblStreet, gbc_lblStreet);

        streetField = new JTextField();
        GridBagConstraints gbc_textField9 = new GridBagConstraints();
        gbc_textField9.insets = new Insets(0, 0, 5, 0);
        gbc_textField9.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField9.gridx = 1;
        gbc_textField9.gridy = 3;
        getContentPane().add(streetField, gbc_textField9);
        streetField.setColumns(10);

        lblCity = new JLabel("City");
        GridBagConstraints gbc_lblCity = new GridBagConstraints();
        gbc_lblCity.anchor = GridBagConstraints.EAST;
        gbc_lblCity.insets = new Insets(0, 0, 5, 5);
        gbc_lblCity.gridx = 0;
        gbc_lblCity.gridy = 4;
        getContentPane().add(lblCity, gbc_lblCity);

        cityField = new JTextField();
        GridBagConstraints gbc_cityField = new GridBagConstraints();
        gbc_cityField.insets = new Insets(0, 0, 5, 0);
        gbc_cityField.fill = GridBagConstraints.HORIZONTAL;
        gbc_cityField.gridx = 1;
        gbc_cityField.gridy = 4;
        getContentPane().add(cityField, gbc_cityField);
        cityField.setColumns(10);

        lblState = new JLabel("State");
        GridBagConstraints gbc_lblState = new GridBagConstraints();
        gbc_lblState.anchor = GridBagConstraints.EAST;
        gbc_lblState.insets = new Insets(0, 0, 5, 5);
        gbc_lblState.gridx = 0;
        gbc_lblState.gridy = 5;
        getContentPane().add(lblState, gbc_lblState);

        stateField = new JTextField();
        GridBagConstraints gbc_stateField = new GridBagConstraints();
        gbc_stateField.insets = new Insets(0, 0, 5, 0);
        gbc_stateField.fill = GridBagConstraints.HORIZONTAL;
        gbc_stateField.gridx = 1;
        gbc_stateField.gridy = 5;
        getContentPane().add(stateField, gbc_stateField);
        stateField.setColumns(10);

        lblZip = new JLabel("Zip");
        GridBagConstraints gbc_lblZip = new GridBagConstraints();
        gbc_lblZip.anchor = GridBagConstraints.EAST;
        gbc_lblZip.insets = new Insets(0, 0, 5, 5);
        gbc_lblZip.gridx = 0;
        gbc_lblZip.gridy = 6;
        getContentPane().add(lblZip, gbc_lblZip);

        zipField = new JTextField();
        GridBagConstraints gbc_zipField = new GridBagConstraints();
        gbc_zipField.insets = new Insets(0, 0, 5, 0);
        gbc_zipField.fill = GridBagConstraints.HORIZONTAL;
        gbc_zipField.gridx = 1;
        gbc_zipField.gridy = 6;
        getContentPane().add(zipField, gbc_zipField);
        zipField.setColumns(10);

        lblBio = new JLabel("Bio");
        GridBagConstraints gbc_lblBio = new GridBagConstraints();
        gbc_lblBio.insets = new Insets(0, 0, 5, 5);
        gbc_lblBio.gridx = 0;
        gbc_lblBio.gridy = 7;
        getContentPane().add(lblBio, gbc_lblBio);

        bioField = new JTextArea();
        GridBagConstraints gbc_bioField = new GridBagConstraints();
        gbc_bioField.insets = new Insets(0, 0, 5, 0);
        gbc_bioField.fill = GridBagConstraints.BOTH;
        gbc_bioField.gridx = 1;
        gbc_bioField.gridy = 7;
        getContentPane().add(bioField, gbc_bioField);

        btnAdd = new JButton("Add");
        GridBagConstraints gbc_btnAdd = new GridBagConstraints();
        gbc_btnAdd.gridx = 1;
        gbc_btnAdd.gridy = 8;
        getContentPane().add(btnAdd, gbc_btnAdd);

        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Add Author Dialog");
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);

        ControllerInterface controller = SystemController.getInstance();
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String firstName = Util.isRequired(firstNameField.getText(), "First name");
                    String lastName = Util.isRequired(lastNameField.getText(), "Last name");
                    String telephone = telephoneField.getText();
                    String street = streetField.getText();
                    String city = cityField.getText();
                    String state = stateField.getText();
                    String zip = Util.isNumericString(zipField.getText(), 5, 5, "Zip code");
                    String bio = bioField.getText();
                    controller.addAuthor(new Author(firstName, lastName, telephone, new Address(street, city, state, zip), bio));
                    JOptionPane.showMessageDialog(null, "Author added");
                    AddBookWindow.getInstance().loadAuthors();
                } catch (ValidationException exception) {
                    JOptionPane.showMessageDialog(AddAuthorDialogBox.this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

}
