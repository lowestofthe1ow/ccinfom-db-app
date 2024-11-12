package BocchiTheGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import BocchiTheGUI.components.CommandDialog;
import BocchiTheGUI.components.abs.DialogUI;
import BocchiTheGUI.components.abs.TableSelectionUI;
import BocchiTheGUI.components.ui.AuditionSelectionUI;
import BocchiTheGUI.components.ui.HireStaffUI;
import BocchiTheGUI.components.ui.TimeSlotMakerUI;

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

    /* Table selection */

    private TableSelectionUI a;

    /* TODO: Move this into CommandDialog maybe? */
    public void updateTable(List<Object[]> b) {
        a.updateTable(b);
    }

    public DialogUI dialogHandler(String event) {
        switch (event) {
            case "hire_staff":
                return new HireStaffUI();
            case "audition":
                a = new AuditionSelectionUI();
                return a;
            case "add_timeslot":
                return new TimeSlotMakerUI();
        }
        return null;
    }

    /* Handling dialog windows */

    private CommandDialog dialog;

    public void createDialog(DialogUI dialogUI, Runnable callback) {
        dialog = new CommandDialog(dialogUI, callback);
    }

    public Object[][] getSQLParameterInputs() {
        return dialog.getSQLParameterInputs();
    }

    public void addDialogButtonListener(ActionListener listener) {
        this.dialog.addButtonListener(listener);
    }

    public void showDialog() {
        dialog.setVisible(true);
    }

    public void closeDialog() {
        if (dialog != null)
            dialog.setVisible(false);
    }

    public boolean isTerminatingCommand(String command) {
        return this.dialog.isTerminatingCommand(command);
    }
}