package org.pluralsight.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeMenuPanel extends JPanel implements ActionListener {

    AppJFrame appJFrame;
    JButton addDeposit, addPayment, ledgerMenu, quitProgram;

    public HomeMenuPanel(AppJFrame appJFrame) {
        this.appJFrame = appJFrame;
        this.setPreferredSize(appJFrame.getPreferredSize()); //sets size of JPanel - or else will be (0,0)

        addDeposit = new JButton("Add Deposit");
        addPayment = new JButton("Add Payment");
        ledgerMenu = new JButton("Ledger Menu");
        quitProgram = new JButton("Quit Program");

        addDeposit.addActionListener(this);
        addPayment.addActionListener(this);
        ledgerMenu.addActionListener(this);
        quitProgram.addActionListener(this);

        this.add(addDeposit);
        this.add(addPayment);
        this.add(ledgerMenu);
        this.add(quitProgram);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addDeposit) {
            System.out.println("Deposits");
        }

        if(e.getSource() == addPayment) {
            System.out.println("Payments");
        }

        if(e.getSource() == ledgerMenu) {
            System.out.println("Ledger");
        }

        if (e.getSource() == quitProgram) {
            System.out.println("Quit program");
        }
    }
}
