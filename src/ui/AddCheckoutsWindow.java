package ui;

import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;

public class AddCheckoutsWindow extends MenusWindow {

    private static AddCheckoutsWindow instance;
    private final ControllerInterface controller;

    private JTextField memberIdField, isbnField;
    private JButton addButton, backButton;

    private AddCheckoutsWindow() {
        this.controller = SystemController.getInstance();
    }

    public synchronized static AddCheckoutsWindow getInstance() {
        if (instance == null) {
            instance = new AddCheckoutsWindow();
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

        add(panel);

        addButton.addActionListener(e -> {
            try {
                String memberId = Util.isRequired(memberIdField.getText(), "Member ID");
                String isbn = Util.isRequired(isbnField.getText(), "ISBN");
                controller.checkout(isbn, memberId);
                JOptionPane.showMessageDialog(AddCheckoutsWindow.this, "Checkout Book successfully.");
            } catch (RuntimeException runtimeException) {
                JOptionPane.showMessageDialog(AddCheckoutsWindow.this, runtimeException.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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

    public void reset() {
        this.memberIdField.setText("");
        this.isbnField.setText("");
    }
}
