package librarysystem;

import java.awt.*;

import javax.swing.*;

import business.ControllerInterface;
import business.SystemController;

public class LibrarySystem extends MenusWindow {
    private static LibrarySystem instance = null;
    private final ControllerInterface controller;
    private JPanel mainPanel;
    private String pathToImage;

    private LibrarySystem() {
        this.controller = SystemController.getInstance();
    }

    public synchronized static LibrarySystem getInstance() {
        if (instance == null) {
            instance = new LibrarySystem();
        }
        return instance;
    }

    @Override
    public void formatContentPane() {
        this.setTitle("Library Management System");
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 1));
        getContentPane().add(mainPanel);
        String currDirectory = System.getProperty("user.dir");
        pathToImage = currDirectory + "/src/librarysystem/library.jpg";
        ImageIcon image = new ImageIcon(pathToImage);
        mainPanel.add(new JLabel(image));
    }
}
