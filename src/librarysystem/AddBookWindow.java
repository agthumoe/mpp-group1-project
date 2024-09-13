package librarysystem;

import business.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class AddBookWindow extends MenusWindow {

    private static AddBookWindow instance;
    private final ControllerInterface controller;

    private JTextField isbnField, titleField, maxCheckoutLengthField, copiesField, authorFirstNameField, authorLastNameField, authorTelephoneField, authorAddressField, authorBioField;
    private JButton addButton, addAuthorButton, backButton;
    private List<Author> authors;
    private AddBookWindow() {
        this.controller = SystemController.getInstance();
    }

    public synchronized static AddBookWindow getInstance() {
        if (instance == null) {
            instance = new AddBookWindow();
        }
        return instance;
    }

    @Override
    public void formatContentPane() {
        setTitle("Add Book");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        authors = new ArrayList<>();

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnLabel.setBounds(10, 20, 80, 25);
        panel.add(isbnLabel);

        isbnField = new JTextField(20);
        isbnField.setBounds(100, 20, 165, 25);
        panel.add(isbnField);

        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setBounds(10, 60, 80, 25);
        panel.add(titleLabel);

        titleField = new JTextField(20);
        titleField.setBounds(100, 60, 165, 25);
        panel.add(titleField);

        JLabel checkoutLengthLabel = new JLabel("Max Checkout Length:");
        checkoutLengthLabel.setBounds(10, 100, 120, 25);
        panel.add(checkoutLengthLabel);

        maxCheckoutLengthField = new JTextField(20);
        maxCheckoutLengthField.setBounds(140, 100, 165, 25);
        panel.add(maxCheckoutLengthField);

        JLabel copiesLabel = new JLabel("Number of Copies:");
        copiesLabel.setBounds(10, 140, 100, 25);
        panel.add(copiesLabel);

        copiesField = new JTextField(20);
        copiesField.setBounds(140, 140, 165, 25);
        panel.add(copiesField);

        JLabel authorFirstNameLabel = new JLabel("Author First Name *:");
        authorFirstNameLabel.setBounds(10, 180, 120, 25);
        panel.add(authorFirstNameLabel);

        authorFirstNameField = new JTextField(20);
        authorFirstNameField.setBounds(140, 180, 165, 25);
        panel.add(authorFirstNameField);

        JLabel authorLastNameLabel = new JLabel("Author Last Name *:");
        authorLastNameLabel.setBounds(10, 220, 120, 25);
        panel.add(authorLastNameLabel);

        authorLastNameField = new JTextField(20);
        authorLastNameField.setBounds(140, 220, 165, 25);
        panel.add(authorLastNameField);

        JLabel authorTelephone = new JLabel("Author Telephone:");
        authorTelephone.setBounds(10, 260, 120, 25);
        panel.add(authorTelephone);

        authorTelephoneField = new JTextField(20);
        authorTelephoneField.setBounds(140, 260, 165, 25);
        panel.add(authorTelephoneField);

        JLabel authorAddress = new JLabel("Author Address:");
        authorAddress.setBounds(10, 300, 120, 25);
        panel.add(authorAddress);

        authorAddressField = new JTextField(20);
        authorAddressField.setBounds(140, 300, 165, 25);
        panel.add(authorAddressField);

        JLabel authorBio = new JLabel("Author Bio:");
        authorBio.setBounds(10, 340, 120, 25);
        panel.add(authorBio);

        authorBioField = new JTextField(20);
        authorBioField.setBounds(140, 340, 165, 25);
        panel.add(authorBioField);

        addButton = new JButton("Add Book");
        addButton.setBounds(140, 380, 120, 25);
        panel.add(addButton);

        backButton = new JButton("Back to Main");
        backButton.setBounds(140, 420, 120, 25);
        panel.add(backButton);

        add(panel);

        // ActionListener to add a book
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String isbn = isbnField.getText();
                String title = titleField.getText();
                int checkoutLength = Integer.parseInt(maxCheckoutLengthField.getText());
                int copies = Integer.parseInt(copiesField.getText());
                Address authorAddress = new Address("1000 N 4th Street", "Fairfield", "Iowa", "52556");
                Author author = new Author(authorFirstNameField.getText(), authorLastNameField.getText(), authorTelephoneField.getText(), authorAddress, authorBioField.getText());
                List<Author> authors = new ArrayList<>();
                authors.add(author);
                if (!isbn.isEmpty() && !title.isEmpty()) {
                    Book b1 = new Book(isbn, title, checkoutLength, authors);
                    controller.addBook(b1);
                    b1.addCopy();
                    JOptionPane.showMessageDialog(AddBookWindow.this, "Book Added Successfully");
                    reset();
                } else {
                }
            }
        });

        backButton.addActionListener(e -> {
            Util.hideAllWindows();
            if (!LibrarySystem.getInstance().isInitialized()) {
                LibrarySystem.getInstance().init();
            }
            LibrarySystem.getInstance().setVisible(true);
            this.setVisible(false);
        });
    }

    public void reset() {
        this.isbnField.setText("");
        this.titleField.setText("");
        this.maxCheckoutLengthField.setText("");
        this.copiesField.setText("");
        this.authorFirstNameField.setText("");
        this.authorLastNameField.setText("");
        this.authorTelephoneField.setText("");
        this.authorAddressField.setText("");
        this.authorBioField.setText("");
    }
}

