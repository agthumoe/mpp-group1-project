package ui;

import business.Author;
import business.Book;
import business.ControllerInterface;
import business.SystemController;
import exceptions.ValidationException;
import ui.components.BackToMainMenuButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddBookWindow extends MenusWindow {

    private static AddBookWindow instance;
    private final ControllerInterface controller;

    private JTextField isbnField, titleField, maxCheckoutLengthField, copiesField;
    private JButton addButton, addAuthorButton, backButton;
    private List<Author> authors;
    private JPanel panel;
    private JList<Author> authorsList;
    private DefaultListModel<Author> authorsModelList;

    private AddBookWindow() {
        this.controller = SystemController.getInstance();
    }

    public synchronized static AddBookWindow getInstance() {
        if (instance == null) {
            instance = new AddBookWindow();
        }
        return instance;
    }

    public void loadAuthors() {
        this.authors = this.controller.getAllAuthors();
        this.authorsModelList.clear();
        this.authorsModelList.addAll(this.authors);
    }

    private void renderLabelAndText(JLabel label, JTextField field, int y) {
        GridBagConstraints labelGbc = new GridBagConstraints();
        labelGbc.ipadx = 10;
        labelGbc.ipady = 10;
        labelGbc.gridx = 0;
        labelGbc.gridy = y;
        labelGbc.anchor = GridBagConstraints.EAST;
        this.panel.add(label, labelGbc);
        GridBagConstraints fieldGbc = new GridBagConstraints();
        fieldGbc.insets = new Insets(10, 10, 10, 10);
        fieldGbc.gridx = 1;
        fieldGbc.gridy = y;
        this.panel.add(field, fieldGbc);
    }

    private void renderLabelAndText(JLabel label, JList<Author> field, int y) {
        field.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Author author = (Author) value;
                String fullName = author.getFirstName() + " " + author.getLastName();
                return super.getListCellRendererComponent(list, fullName, index, isSelected, cellHasFocus);
            }
        });
        GridBagConstraints labelGbc = new GridBagConstraints();
        labelGbc.ipadx = 10;
        labelGbc.ipady = 10;
        labelGbc.gridx = 0;
        labelGbc.gridy = y;
        labelGbc.anchor = GridBagConstraints.EAST;
        this.panel.add(label, labelGbc);
        GridBagConstraints fieldGbc = new GridBagConstraints();
        fieldGbc.insets = new Insets(10, 10, 10, 10);
        fieldGbc.gridx = 1;
        fieldGbc.gridy = y;
        fieldGbc.fill = GridBagConstraints.HORIZONTAL;
        JScrollPane scrollPane = new JScrollPane(field);
        this.panel.add(scrollPane, fieldGbc);
    }

    @Override
    public void formatContentPane() {
        setTitle("Add Book");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        this.panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnField = new JTextField(20);
        this.renderLabelAndText(isbnLabel, isbnField, 0);

        JLabel titleLabel = new JLabel("Title:");
        titleField = new JTextField(20);
        this.renderLabelAndText(titleLabel, titleField, 1);

        JLabel checkoutLengthLabel = new JLabel("Max Checkout Length:");
        maxCheckoutLengthField = new JTextField(20);
        this.renderLabelAndText(checkoutLengthLabel, maxCheckoutLengthField, 2);

        JLabel copiesLabel = new JLabel("Number of Copies:");
        copiesField = new JTextField(20);
        this.renderLabelAndText(copiesLabel, copiesField, 3);

        JLabel authorLabel = new JLabel("Author:");
        this.authorsModelList = new DefaultListModel<>();
        this.authorsList = new JList<>(this.authorsModelList);
        this.authorsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.renderLabelAndText(authorLabel, authorsList, 4);

        this.addAuthorButton = new JButton("Add Author");
        GridBagConstraints addAuthorButtonGbc = new GridBagConstraints();
        addAuthorButtonGbc.insets = new Insets(10, 10, 10, 10);
        addAuthorButtonGbc.gridx = 1;
        addAuthorButtonGbc.fill = GridBagConstraints.HORIZONTAL;
        addAuthorButtonGbc.gridy = 5;
        panel.add(addAuthorButton, addAuthorButtonGbc);

        addAuthorButton.addActionListener((e) -> {
            JDialog dialog = new AddAuthorDialogBox();
            dialog.setVisible(true);
        });

        GridBagConstraints backGbc = new GridBagConstraints();
        backGbc.insets = new Insets(0, 10, 0, 10);
        backGbc.gridx = 0;
        backGbc.gridy = 6;
        backGbc.anchor = GridBagConstraints.EAST;
        GridBagConstraints addGbc = new GridBagConstraints();
        addGbc.insets = new Insets(10, 10, 10, 10);
        addGbc.gridx = 1;
        addGbc.gridy = 6;
        addGbc.anchor = GridBagConstraints.WEST;

        addButton = new JButton("Save");
        panel.add(addButton, addGbc);

        backButton = new BackToMainMenuButton();
        panel.add(backButton, backGbc);

        add(panel);

        // ActionListener to add a book
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String isbn = Util.isRequired(isbnField.getText(), "Book isbn");
                    String title = Util.isRequired(titleField.getText(), "Book title");
                    int checkoutLength = Util.isNumber(maxCheckoutLengthField.getText(), 1, 100, "Max Checkout Length");
                    int copies = Util.isNumber(copiesField.getText(), 1, 100, "Number of Copies");
                    List<Author> authors = Util.isNotEmpty(authorsList.getSelectedValuesList(), "Authors");
                    Book b1 = new Book(isbn, title, checkoutLength, authors);
                    controller.addBook(b1);
                    b1.addCopy(copies);
                    JOptionPane.showMessageDialog(AddBookWindow.this, "Book Added Successfully");
                    reset();
                } catch (ValidationException exception) {
                    JOptionPane.showMessageDialog(AddBookWindow.this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        this.loadAuthors();
    }

    public void reset() {
        this.isbnField.setText("");
        this.titleField.setText("");
        this.maxCheckoutLengthField.setText("");
        this.copiesField.setText("");
        this.loadAuthors();
    }
}

