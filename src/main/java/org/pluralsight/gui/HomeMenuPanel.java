package org.pluralsight.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class HomeMenuPanel extends JPanel {

    AppJFrame appJFrame;
    JButton[] buttons = new JButton[4];
    String[] buttonTitles = {"Add Deposit", "Add Payment", "Ledger Screen", "Quit Program"};

    public HomeMenuPanel(AppJFrame appJFrame) {
        this.appJFrame = appJFrame;
        this.setPreferredSize(appJFrame.getMinimumSize()); //sets size of JPanel - or else will be (0,0)
        for(int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonTitles[i]);
            this.add(buttons[i]);
        }
    }
}
