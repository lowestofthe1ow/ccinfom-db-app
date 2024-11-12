package BocchiTheGUI;

import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public abstract class DialogUI extends JPanel {
    private ArrayList<JButton> buttons;
    private JPanel buttonPanel;
    private String dialogTitle;

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

        this.add(buttonPanel);
    }

    public DialogUI(String dialogTitle) {
        this.dialogTitle = dialogTitle;
        this.buttons = new ArrayList<>();
        this.buttonPanel = new JPanel();

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
    }
}
