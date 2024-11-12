package BocchiTheGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import BocchiTheGUI.components.BocchiTheMenuBar;
import BocchiTheGUI.components.CommandDialog;
import BocchiTheGUI.components.abs.DialogUI;

public class GUI extends JFrame {
    private BocchiTheMenuBar menuBar;

    public GUI() {
        /* Set up menu bar */
        this.menuBar = new BocchiTheMenuBar();
        this.setJMenuBar(menuBar);

        ImageIcon gifIcon = new ImageIcon(
                getClass().getClassLoader().getResource("BocchiTheAssets/bocchi-the-rock-bocchi.gif"));
        JLabel gifLabel = new JLabel(gifIcon);

        /* Set up frame */
        this.setTitle("Bocchi the GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        /* TODO: BocchiTheBody */
        // this.add(new BocchiTheBody(), BorderLayout.CENTER);

        this.setSize(960, 600);
        this.add(gifLabel, BorderLayout.CENTER);

        this.setVisible(true);

    }

    public void setWindowListener(WindowAdapter windowAdapter) {
        this.addWindowListener(windowAdapter);
    }

    public void setMenuListener(ActionListener actionListener) {
        this.menuBar.addMenuListener(actionListener);
    }

    /* Handling dialog windows */

    private CommandDialog dialog;

    public void createDialog(DialogUI dialogUI, Runnable callback) {
        dialog = new CommandDialog(dialogUI, callback);
    }

    public void showDialog() {
        dialog.setVisible(true);
    }

    public void closeDialog() {
        if (dialog != null)
            dialog.setVisible(false);
    }
}