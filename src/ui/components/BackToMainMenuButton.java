package ui.components;

import ui.LibrarySystem;
import ui.Util;

import javax.swing.*;

public class BackToMainMenuButton extends JButton {
    public BackToMainMenuButton() {
        super("Back to Main Menu");
        this.addActionListener(e -> {
            Util.hideAllWindows();
            if (!LibrarySystem.getInstance().isInitialized()) {
                LibrarySystem.getInstance().init();
            }
            LibrarySystem.getInstance().setVisible(true);
        });
    }
}
