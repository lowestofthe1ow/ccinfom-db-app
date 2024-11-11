package BocchiTheGUI;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

public class CommandDialog extends JDialog {
    private DialogUI dialogUI;

    /**
     * Constructs a {@link JDialog} instance that contains a {@link DialogUI}.
     * 
     * @param dialogUI The {@link DialogUI} object to display within the dialog
     *                 window
     * @param onReady  Callback function to call once the UI has been loaded into
     *                 the dialog window
     */
    public CommandDialog(DialogUI dialogUI, Runnable onReady) {
        SwingUtilities.invokeLater(() -> {
            this.dialogUI = dialogUI;

            this.getContentPane().setLayout(new BorderLayout());
            this.setLocationRelativeTo(null);
            this.setModal(true);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setSize(400, 360);
            this.getContentPane().add(dialogUI);

            onReady.run();
        });
    }

    /**
     * Attaches a {@link ActionListener} to all {@link JButton}s tied to the dialog
     * UI.
     * 
     * @param listener The listener to attach
     * @see DialogUI#addButtonListener(ActionListener)
     */
    protected void addButtonListener(ActionListener listener) {
        this.dialogUI.addButtonListener(listener);
    }

    /**
     * {@return an array of objects that represent the parameters to pass to the SQL
     * query}
     * 
     * @see DialogUI#getSQLParameterInputs()
     */
    public Object[] getSQLParameterInputs() {
        return dialogUI.getSQLParameterInputs();
    }
}
