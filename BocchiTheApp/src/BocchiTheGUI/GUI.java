package BocchiTheGUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class GUI extends JFrame {
    // Main components
    private BocchiTheMenuBar menuBar;
    // The other pages

    public GUI() {
        
        menuBar = new BocchiTheMenuBar();
        
        this.setJMenuBar(menuBar);
        
        ImageIcon gifIcon = new ImageIcon(
                getClass().getClassLoader().getResource("BocchiTheAssets/bocchi-the-rock-bocchi.gif"));
        JLabel gifLabel = new JLabel(gifIcon);

        // Set up the frame
        this.setTitle("Bocchi the GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
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
    
    // TABLE SELECTION GALORE
    private TableSelectionUI a;
    public void updateTable(List<Object[]> b) {
    	a.updateTable(b);
    }
    public DialogUI dialogHandler(String event) {
    	switch(event) {
    	case "hire_staff":
    		return new HireStaffUI();
    	case "audition":
    		a = new AuditionSelectionUI();
    		return a;
    	}
		return null;
    }

    /* LOWEST'S WEIRD NEW DIALOG STUFF */

    private CommandDialog dialog;

    public void createDialog(DialogUI dialogUI, Runnable callback) {
        dialog = new CommandDialog(dialogUI, callback);
    }

    public void closeDialog() {
        if (dialog != null)
            dialog.setVisible(false);
    }

    public Object[] getSQLParameterInputs() {
        return dialog.getSQLParameterInputs();
    }

    public void addDialogButtonListener(ActionListener listener) {
        this.dialog.addButtonListener(listener);
    }
    
    public void showDialog() {
        dialog.setVisible(true);
    }

    /* END OF LOWEST'S WEIRD NEW DIALOG STUFF */

    


}
