package BocchiTheGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import BocchiTheGUI.elements.abstracts.PaneUI;
import BocchiTheGUI.elements.components.BocchiTheMenuBar;
import BocchiTheGUI.elements.components.BocchiTheTabbedPane;
import BocchiTheGUI.elements.components.CommandDialog;
import BocchiTheGUI.elements.ui.tab.HomeTabUI;

public class GUI extends JFrame {
    private BocchiTheMenuBar menuBar;
    private BocchiTheTabbedPane tabbedPane;
    private HomeTabUI generateReportUI;
    private HashMap<String, CommandDialog> dialogs = new HashMap<>();

    public GUI() {
        /* Set up window frame */
        //this.setTitle("");
        this.setLayout(new BorderLayout());
        this.setSize(960, 600);

        /* Set up menu bar */
        this.menuBar = new BocchiTheMenuBar();
        this.setJMenuBar(menuBar);

        JPanel homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());

        this.generateReportUI = new HomeTabUI();

        homePanel.add(generateReportUI, BorderLayout.CENTER);

        this.tabbedPane = new BocchiTheTabbedPane(homePanel);
        this.add(tabbedPane);

        /* Use the system exit call */
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setVisible(true);
    }

    public void setGenerateReportsListener(ActionListener actionListener) {
        this.generateReportUI.addButtonListener(actionListener);
        this.tabbedPane.addTabbedPaneListener(actionListener);
    }

    /**
     * Adds an {@link ActionListener} to the menu bar.
     * 
     * @param actionListener
     */
    public void setMenuBarListener(ActionListener actionListener) {
        this.menuBar.addMenuListener(actionListener);
    }

    public void addTab(PaneUI dialogUI, String name) {
        tabbedPane.newTab(name, dialogUI);
    }

    /**
     * Creates a new {@link CommandDialog} using a given {@link PaneUI}. The
     * created object is stored in a map with the identifier string as the key,
     * allowing it to be referenced later with {@link #showDialog(String)} and
     * {@link #closeDialog(String)}
     * 
     * @param dialogUI The UI to attach to the dialog window
     * @param name     The identifier string for the dialog window
     * @param callback The callback function to execute after creating the dialog
     */
    public void createDialog(PaneUI dialogUI, String name, Runnable callback) {
        dialogs.put(name, new CommandDialog(dialogUI, callback));
    }

    /**
     * Shows a specific dialog stored in the window.
     * 
     * @param name The identifier string for the dialog window
     */
    public void showDialog(String name) {
        CommandDialog dialog = dialogs.get(name);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }

    /*
     * TODO: A closeDialog(String) function that closes a single dialog instance
     * It is currently not needed, so I removed it
     */

    /** Closes all dialogs stored in the window */
    public void closeAllDialogs() {
        dialogs.forEach((key, dialog) -> {
            dialogs.get(key).setVisible(false);
        });

        dialogs.clear();
    }

    /**
     * Closes all dialogs stored in the window except one specified.
     * 
     * @param name     The identifier for the dialog to keep
     * @param callback The callback function to execute after closing the dialogs
     */
    public void closeAllDialogsExcept(String name, Consumer<PaneUI> callback) {
        CommandDialog keep = dialogs.get(name);

        dialogs.forEach((key, dialog) -> {
            if (!key.equals(name)) {
                dialogs.get(key).setVisible(false);
            }
        });
        dialogs.clear();
        dialogs.put(name, keep);

        callback.accept(keep.getPanelUI());
    }
}