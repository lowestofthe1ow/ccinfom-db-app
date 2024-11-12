package BocchiTheGUI.components;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import BocchiTheGUI.components.abs.DialogUI;

public class CommandDialog extends JDialog {
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
            this.getContentPane().setLayout(new BorderLayout());
            this.setLocationRelativeTo(null);
            this.setModal(true);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            this.setSize(400, 360);
            this.getContentPane().add(dialogUI);
            this.setTitle(dialogUI.getDialogTitle());
            this.pack();
            onReady.run();
        });
    }
}