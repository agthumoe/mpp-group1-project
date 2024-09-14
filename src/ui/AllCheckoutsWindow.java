package ui;

import business.Book;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;

public class AllCheckoutsWindow extends MenusWindow {

    //    private static final long serialVersionUID = 1L;
    private static AllCheckoutsWindow instance;
    private final ControllerInterface controller;

    private JTextField memberIdField, isbnField;
    private JButton addButton, backButton;
//    private JLabel statusLabel;

    private AllCheckoutsWindow() {
        this.controller = SystemController.getInstance();
    }

    public synchronized static AllCheckoutsWindow getInstance() {
        if (instance == null) {
            instance = new AllCheckoutsWindow();
        }
        return instance;
    }

    @Override
    public void formatContentPane() {
        setTitle("Book Checkout");
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel memberIdLabel = new JLabel("Member ID:");
        memberIdLabel.setBounds(10, 20, 80, 25);
        panel.add(memberIdLabel);

        memberIdField = new JTextField(20);
        memberIdField.setBounds(100, 20, 165, 25);
        panel.add(memberIdField);

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnLabel.setBounds(10, 60, 80, 25);
        panel.add(isbnLabel);

        isbnField = new JTextField(20);
        isbnField.setBounds(100, 60, 165, 25);
        panel.add(isbnField);

        addButton = new JButton("Checkout");
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
            String isbn = isbnField.getText();
            try {
                Book book = controller.checkout(isbn, memberId);
                JOptionPane.showMessageDialog(AllCheckoutsWindow.this, "Checkout Book successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Book Checkout Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.reset();
        });

        backButton.addActionListener(e -> {
            Util.hideAllWindows();
            LibrarySystem.getInstance().setVisible(true);
            this.setVisible(false);
        });
    }

    private void reset() {
        this.memberIdField.setText("");
        this.isbnField.setText("");
    }
}
