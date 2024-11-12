package BocchiTheGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class DialogUI extends JPanel {
    private ArrayList<JButton> buttons;
    private JPanel buttonPanel;
    private String dialogTitle;

    private ArrayList<String> terminatingCommands;

    protected void addTerminatingActionCommands(String... commands) {
        for (String command : commands) {
            terminatingCommands.add(command);
        }
    }

    public boolean isTerminatingCommand(String command) {
        return terminatingCommands.contains(command);
    }

    /**
     * {@return a 2D array of objects that represent the parameters to pass to SQL
     * query or queries} Each inner array represents a single query.
     */
    public abstract Object[][] getSQLParameterInputs();

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
    protected void setButtonActionCommands(String... actionCommands) {
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
    protected void addButtons(String... buttonLabels) {
        for (String actionCommand : buttonLabels) {
            JButton button = new JButton();
            button.setText(actionCommand);
            this.buttons.add(button);
        }

        this.buttons.forEach((button) -> {
            buttonPanel.add(button);
        });

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public DialogUI(String dialogTitle) {
        this.dialogTitle = dialogTitle;
        this.terminatingCommands = new ArrayList<>();
        this.buttons = new ArrayList<>();
        this.buttonPanel = new JPanel();
        this.setLayout(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
    }
}