package org.pluralsight.gui;

import javax.swing.*;
import java.awt.*;

public class AppJFrame extends JFrame {
    private int screenWidth = 700;
    private int screenHeight = 500;

    public AppJFrame() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(3);
        window.setResizable(false);
        window.setTitle("Accounting Ledger Application");
        window.setPreferredSize(new Dimension(screenWidth, screenHeight));
        window.setBackground(Color.gray);
        //need to make classes that will return JPanels
        //to populate the JFrame
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    public static void main(String[] args) {
        AppJFrame appJFrame = new AppJFrame();
    }
}
