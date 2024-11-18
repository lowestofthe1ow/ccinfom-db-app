package BocchiTheGUI.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

public class BocchiTheTabbedPane extends JTabbedPane {
    private ArrayList<JPanel> openTabs;
    private JMenuBar newTabMenu;

    private JMenuBar createNewTabMenu(String... menuItems) {
        JMenuBar newTab = new JMenuBar();
        JMenu menu = new JMenu("+");

        newTab.add(menu);
        newTab.setFont(new Font("Arial", Font.PLAIN, 18));
        newTab.setFocusable(false);

        for (int i = 0; i < menuItems.length; i += 2) {
            JMenuItem menuItem = new JMenuItem(menuItems[i]);
            menuItem.setActionCommand(menuItems[i + 1]);
            menu.add(menuItem);
        }

        return newTab;
    }

    /**
     * Creates a close button to add to a specific tab
     * 
     * @param label    The label to add to the button
     * @param listener The listener to attach
     * @return The created button
     */
    private JButton createCloseButton(String label, ActionListener listener) {
        JButton button = new JButton(label);

        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setFocusable(false);
        button.setBorder(new EmptyBorder(0, 0, 0, 0));
        button.addActionListener(listener);
        button.setBackground(new Color(255, 255, 255, 128));

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
        JButton closeButton = createCloseButton("×", (e) -> {
            int index = this.openTabs.indexOf(tabHeader) + 1;
            if (index == this.getSelectedIndex()) {
                this.setSelectedIndex(0);
            }
            /* Remove the tab from the pane (note index + 1 to account for home) */
            this.removeTabAt(index);
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
                panel,
                null,
                this.openTabs.size());

        this.setTabComponentAt(this.openTabs.size(), tabHeader);

        this.setSelectedIndex(this.openTabs.size());
    }

    /**
     * Sets a listener that listens to the new tab button.
     * 
     * @param actionListener The listener to attach
     */
    public void addTabbedPaneListener(ActionListener actionListener) {
        for (int i = 0; i < this.newTabMenu.getMenu(0).getItemCount(); i++) {
            this.newTabMenu.getMenu(0).getItem(i).addActionListener(actionListener);
        }
    }

    public BocchiTheTabbedPane(JPanel homePanel) {
        this.addTab("Home", homePanel);
        this.addTab(null, null);

        this.newTabMenu = createNewTabMenu(
                "Daily performer sales", "dialog/performer_revenue",
                "Monthly livehouse sales", "dialog/monthly_livehouse_revenue",
                "Monthly rental sales", null,
                "Weekly livehouse schedule", "report/livehouse_schedule",
                "Staff salary report", null);

        this.setTabComponentAt(1, this.newTabMenu);
        this.setEnabledAt(1, false);

        this.openTabs = new ArrayList<>();
    }
}
