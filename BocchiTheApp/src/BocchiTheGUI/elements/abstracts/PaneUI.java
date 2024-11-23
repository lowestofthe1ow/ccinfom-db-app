package BocchiTheGUI.elements.abstracts;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class PaneUI extends JPanel {
    private String dialogTitle;
    private String rootDialogUI;
    protected JPanel buttonPanel;
    protected List<JButton> buttons;
    private List<String> terminatingCommands;
    private List<String> disableImmuneCommands;

    /** {@return the action command of the first button tied to this UI} */
    protected String getDefaultButtonCommand() {
        return this.buttons.get(0).getActionCommand();
    }

    protected void setRoot(String rootDialogUI) {
        this.rootDialogUI = rootDialogUI;
    }

    public String getRoot() {
        return this.rootDialogUI;
    }

    /**
     * {@return a 2D array of objects that represent the parameters to pass to SQL
     * query or queries} Each inner array represents a single query.
     */
    public abstract Object[][] getSQLParameterInputs();

    /**
     * Enables or disables all buttons in the UI.
     * 
     * @param enabled {@code true} to enable all buttons, {@code false} to disable
     *                all buttons
     */
    public void enableAllButtons() {
        buttons.forEach((button) -> {
            button.setEnabled(true);
        });
    }

    /**
     * Enables or disables all buttons in the UI except for specific commands
     * 
     * @param enabled {@code true} to enable all buttons, {@code false} to disable
     *                all buttons
     */
    public void disableAllButtons() {
        buttons.forEach((button) -> {
            if (!disableImmuneCommands.contains(button.getActionCommand()))
                button.setEnabled(false);
        });
    }

    /**
     * Adds an action command string to the list of action commands that cannot be
     * disabled.
     * 
     * @param commands The action command strings to add
     */
    protected void addDisableImmuneCommands(String... commands) {
        for (String command : commands) {
            disableImmuneCommands.add(command);
        }
    }

    /**
     * Adds an action command string to the list of action commands that close the
     * dialog window when triggered.
     * 
     * @param commands The action command strings to add
     */
    protected void addTerminatingCommands(String... commands) {
        for (String command : commands) {
            terminatingCommands.add(command);
        }
    }

    /**
     * Checks if an action command string is part of the list of terminating
     * commands that close the dialog window when triggered.
     * 
     * @param command The action command to check
     * @return {@code true} if the string is a terminating command or contains
     *         {@code "button/next"} (a special identifier), {@code false}
     *         otherwise
     */
    public boolean isTerminatingCommand(String command) {
        return command.contains("button/next") || terminatingCommands.contains(command);
    }

    public String getDialogTitle() {
        return this.dialogTitle;
    }

    /**
     * Attaches a {@link ActionListener} to all {@link JButton}s tied to the dialog.
     * 
     * @param listener The listener to attach
     */
    public void addButtonListener(ActionListener listener) {
        this.buttons.forEach((button) -> {
            button.addActionListener(listener);
        });
    }

    /**
     * Sets action command strings for each {@link JButton} in the same order they
     * were created in {@link #addButtons(String[])}
     * 
     * @param actionCommands Array of action command strings to add to each button
     */
    public void setButtonActionCommands(String... actionCommands) {
        int i = 0;
        for (String actionCommand : actionCommands) {
            this.buttons.get(i).setActionCommand(actionCommand);
            i++;
        }
    }

    /**
     * Creates a panel containing one or more {@link JButton}s and adds it to the
     * dialog.
     * 
     * @param buttonLabels The labels for each button
     */
    public void addButtons(String... buttonLabels) {
        for (String actionCommand : buttonLabels) {
            JButton button = new JButton();
            button.setText(actionCommand);
            this.buttons.add(button);
        }

        buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        this.buttons.forEach((button) -> {
            buttonPanel.add(button);
        });

        this.add(buttonPanel);
    }

    /*
     * protected void setButtonPanelLocation(String location) {
     * this.remove(buttonPanel);
     * this.add(buttonPanel, location);
     * }
     */
    public PaneUI(String dialogTitle) {
        this.dialogTitle = dialogTitle;
        this.terminatingCommands = new ArrayList<>();
        this.disableImmuneCommands = new ArrayList<>();
        this.buttons = new ArrayList<>();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}