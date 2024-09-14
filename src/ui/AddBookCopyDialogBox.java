package ui;

import business.Book;
import business.ControllerInterface;
import business.SystemController;
import exceptions.ValidationException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AddBookCopyDialogBox extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JTextField numberOfCopiesField;
    private JComboBox<Book> cbbBooks;

    /**
     * Create the dialog.
     */
    public AddBookCopyDialogBox() {
        this.setTitle("Add Book Copy");
        ControllerInterface controller = SystemController.getInstance();
        List<Book> books = controller.getAllBooks();
        setBounds(100, 100, 450, 166);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        GridBagLayout gbl_contentPanel = new GridBagLayout();
        gbl_contentPanel.columnWidths = new int[]{0, 0, 0};
        gbl_contentPanel.rowHeights = new int[]{0, 0, 0};
        gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
        contentPanel.setLayout(gbl_contentPanel);
        {
            JLabel lblBook = new JLabel("Select Book");
            GridBagConstraints gbc_lblBook = new GridBagConstraints();
            gbc_lblBook.insets = new Insets(0, 0, 5, 5);
            gbc_lblBook.anchor = GridBagConstraints.EAST;
            gbc_lblBook.gridx = 0;
            gbc_lblBook.gridy = 0;
            contentPanel.add(lblBook, gbc_lblBook);
        }
        {
            cbbBooks = new JComboBox<>();
            GridBagConstraints gbc_cbbBooks = new GridBagConstraints();
            gbc_cbbBooks.insets = new Insets(0, 0, 5, 0);
            gbc_cbbBooks.fill = GridBagConstraints.HORIZONTAL;
            gbc_cbbBooks.gridx = 1;
            gbc_cbbBooks.gridy = 0;
            contentPanel.add(cbbBooks, gbc_cbbBooks);
            cbbBooks.setRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Book book = (Book) value;
                    String bookName = book.getTitle() + "(" + book.getIsbn() + ")";
                    return super.getListCellRendererComponent(list, bookName, index, isSelected, cellHasFocus);
                }
            });
            for (Book book : books) {
                cbbBooks.addItem(book);
            }
        }
        {
            JLabel lblNumberOfCopies = new JLabel("Number of Copies");
            GridBagConstraints gbc_lblNumberOfCopies = new GridBagConstraints();
            gbc_lblNumberOfCopies.anchor = GridBagConstraints.EAST;
            gbc_lblNumberOfCopies.insets = new Insets(0, 0, 0, 5);
            gbc_lblNumberOfCopies.gridx = 0;
            gbc_lblNumberOfCopies.gridy = 1;
            contentPanel.add(lblNumberOfCopies, gbc_lblNumberOfCopies);
        }
        {
            numberOfCopiesField = new JTextField();
            GridBagConstraints gbc_numberOfCopiesField = new GridBagConstraints();
            gbc_numberOfCopiesField.fill = GridBagConstraints.HORIZONTAL;
            gbc_numberOfCopiesField.gridx = 1;
            gbc_numberOfCopiesField.gridy = 1;
            contentPanel.add(numberOfCopiesField, gbc_numberOfCopiesField);
            numberOfCopiesField.setColumns(10);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("Save");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            int numberOfCopies = Util.isNumber(numberOfCopiesField.getText(), 1, 100, "Number of copies");
                            Book book = (Book) cbbBooks.getSelectedItem();
                            if (book == null) {
                                throw new ValidationException("Must select a book");
                            }
                            controller.makeCopy(book, numberOfCopies);
                            JOptionPane.showMessageDialog(AddBookCopyDialogBox.this, "Book copied successfully");
                            AllBooksWindow.getInstance().loadData();
                            AddBookCopyDialogBox.this.setVisible(false);
                            AddBookCopyDialogBox.this.dispose();
                        } catch (ValidationException e1) {
                            JOptionPane.showMessageDialog(AddBookCopyDialogBox.this, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        AddBookCopyDialogBox.this.setVisible(false);
                        AddBookCopyDialogBox.this.dispose();
                    }
                });
                buttonPane.add(cancelButton);
            }
        }
    }

}
