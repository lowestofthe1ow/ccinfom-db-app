package BocchiTheGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.HashMap;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import BocchiTheGUI.components.BocchiTheBody;
import BocchiTheGUI.components.BocchiTheMenuBar;
import BocchiTheGUI.components.CommandDialog;
import BocchiTheGUI.components.abs.DialogUI;
import raven.datetime.component.date.DatePicker;

public class GUI extends JFrame {
    private BocchiTheMenuBar menuBar;

    public GUI() {
        /* Set up menu bar */
        this.menuBar = new BocchiTheMenuBar();
        
        

        /* Set up frame */
        this.setTitle("Bocchi the GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        /* TODO: Make this better, would love to add a '+' which would add a tab */
        JPanel f = new BocchiTheBody("Performance",
        							"Equipment Type",
        							"Equipment",
        							"Staff",
        							"Performance Timeslot",
        							"Position Type",
        							"Audition",
        							"Performance",
        							"Performance Revenue",
        							"Equipment Rental",
        							"Staff Assignment",
        							"Staff Position");

        
    
        this.setJMenuBar(menuBar);
        this.add(f, BorderLayout.CENTER);
        /*
        ImageIcon gifIcon = new ImageIcon(
                getClass().getClassLoader().getResource("BocchiTheAssets/bocchi-the-rock-bocchi.gif"));
          JLabel gifLabel = new JLabel(gifIcon);
          this.add(gifLabel, BorderLayout.SOUTH);
         */ 
        
        this.setSize(960, 600);
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
        dialogs.get(name).pack();
        dialogs.get(name).setLocationRelativeTo(null);
        dialogs.get(name).setVisible(true);
    }

    public void closeDialog(String name) {
        dialogs.get(name).setVisible(false);
    }

    public void closeAllDialogs() {
        dialogs.forEach((key, dialog) -> {
            dialogs.get(key).setVisible(false);
        });

        dialogs.clear();
    }

    /* TODO: Rewrite, this sucks lmao */
    public void closeAllDialogsExcept(String dialogName, Consumer<DialogUI> callback) {
        CommandDialog root = dialogs.get(dialogName);

        dialogs.forEach((key, dialog) -> {
            if (!key.equals(dialogName)) {
                dialogs.get(key).setVisible(false);
            }
        });
        dialogs.clear();
        dialogs.put(dialogName, root);

        callback.accept(root.getPanelUI());
    }

    /* TODO: Decide if we want to overwrite the UI or create a new window */
    public void setDialog(String name, DialogUI thing) {
        dialogs.get(name).setPanelUI(thing);
    }
}