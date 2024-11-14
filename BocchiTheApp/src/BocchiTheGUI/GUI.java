package BocchiTheGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.HashMap;

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

    private HashMap<String, CommandDialog> dialogs = new HashMap<>();

    public void createDialog(DialogUI dialogUI, String name, Runnable callback) {
        dialogs.put(name, new CommandDialog(dialogUI, callback));
    }

    public void showDialog(String name) {
        dialogs.get(name).setVisible(true);
    }

    public void closeDialog(String name) {
        dialogs.get(name).setVisible(false);
    }

    public void closeAllDialogs() {
        dialogs.forEach((key, dialog) -> {
            dialogs.get(key).setVisible(false);
        });
    }

    /* TODO: Decide if we want to overwrite the UI or create a new window */
    public void setDialog(String name, DialogUI thing) {
        dialogs.get(name).setPanelUI(thing);
    }
}