package ui.components;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(10, 10, getWidth() - 20, getHeight() - 20);
    }
}
