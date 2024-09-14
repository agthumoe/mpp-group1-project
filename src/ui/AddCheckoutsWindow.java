package ui;

import business.Book;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import java.awt.*;

public class AddCheckoutsWindow extends MenusWindow {

    //    private static final long serialVersionUID = 1L;
    private static AddCheckoutsWindow instance;
    private final ControllerInterface controller;

    private JTextField memberIdField, isbnField;
    private JButton addButton, backButton;
//    private JLabel statusLabel;

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

        CustomPanel panel = new CustomPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        Font labelFont = new Font("Arial", Font.BOLD, 12);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        JLabel memberIdLabel = new JLabel("Member ID:");
        memberIdLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(memberIdLabel, gbc);

        memberIdField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(memberIdField, gbc);

        JLabel isbnLabel = new JLabel("ISBN:");
        isbnLabel.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(isbnLabel, gbc);

        isbnField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(isbnField, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));

        addButton = new JButton("Checkout");
        buttonPanel.add(addButton);

        backButton = new JButton("Back to Main");
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);

//        statusLabel = new JLabel("");
//        statusLabel.setBounds(10, 420, 300, 25);
//        panel.add(statusLabel);

        add(panel);

        addButton.addActionListener(e -> {
            String memberId = memberIdField.getText();
            String isbn = isbnField.getText();
            try {
                Book book = controller.checkout(isbn, memberId);
                JOptionPane.showMessageDialog(AddCheckoutsWindow.this, "Checkout Book successfully.");
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
