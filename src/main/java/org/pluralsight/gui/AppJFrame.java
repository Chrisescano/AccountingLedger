package org.pluralsight.gui;

import javax.swing.*;
import java.awt.*;

public class AppJFrame extends JFrame {
    private int screenWidth = 700;
    private int screenHeight = 500;

    public HomeMenuPanel homeMenu;

    public AppJFrame() {
        //basic settings
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes on clicking x
        this.setResizable(false);
        this.setTitle("Accounting Ledger Application");
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.getContentPane().setBackground(Color.black);

        homeMenu = new HomeMenuPanel(this);
        this.add(homeMenu);

        //settings to make the JFrame visible
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new AppJFrame();
    }
}
