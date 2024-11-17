package BocchiTheGUI.components;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class BocchiTheTabbedPane extends JTabbedPane {
    private List<JPanel> openTabs;

    /**
     * Creates a control button in the tabbed pane (close tab or new tab)
     * 
     * @param label    The label to add to the button
     * @param listener The listener to attach
     * @return The created button
     */
    private JButton createControlButton(String label, ActionListener listener) {
        JButton button = new JButton(label);

        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusable(false);
        // button.setBorder(new EmptyBorder(0, 0, 0, 0));
        button.addActionListener(listener);

        return button;
    }

    public void newTab(String label, JPanel panel) {
        /* Create the tab header (will be set as the tab component at the index) */
        JPanel tabHeader = new JPanel();
        tabHeader.setOpaque(false);
        tabHeader.setBorder(new EmptyBorder(0, 0, 0, 0));
        tabHeader.setBackground(this.getBackground());

        /* Add the tab label */
        tabHeader.add(new JLabel(label));

        /* Create the close tab button and add it to the tab header */
        JButton closeButton = createControlButton("×", (e) -> {
            /* Remove the tab from the pane (note index + 1 to account for home) */
            this.removeTabAt(this.openTabs.indexOf(tabHeader) + 1);
            /* Remove the tab from the local list */
            this.openTabs.remove(tabHeader);
        });
        tabHeader.add(closeButton);

        /* Add the tab to the local list to keep track of indices */
        this.openTabs.add(tabHeader);

        /* Add the tab to the tabbed pane right before the new tab button */
        this.insertTab(
                null,
                null,
                new JPanel(),
                null,
                this.openTabs.size());

        this.setTabComponentAt(this.openTabs.size(), tabHeader);
    }

    public BocchiTheTabbedPane() {
        this.addTab("Home", null);
        this.addTab(null, null);

        JButton newTabButton = createControlButton("+", (e) -> {
            this.newTab("New tab", new JPanel());
            // this.add(createMenu("Thing", "tab/thing"));
        });

        this.setTabComponentAt(1, newTabButton);
        this.setEnabledAt(1, false);

        this.openTabs = new ArrayList<>();
    }
}
