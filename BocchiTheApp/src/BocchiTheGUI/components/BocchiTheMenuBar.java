package BocchiTheGUI.components;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class BocchiTheMenuBar extends JMenuBar {
    private List<JMenu> menuTitles;

    BocchiTheMenuBar() {
        menuTitles = new ArrayList<>();

        menuTitles.add(createMenu("Staff",
                "Hire Staff", "hire_staff",
                "Remove Staff", "remove_staff",
                "Update Position", "update_position"));
        menuTitles.add(createMenu("Configure",
                "Add Performer", "add_performer",
                "Add Timeslot", "add_timeslot",
                "Rental", "rental",
                "Audition", "audition"));
        menuTitles.add(createMenu("Finance",
                "Record Revenue", "record_revenue",
                "Generate Reports", "generate_reports"));

        for (JMenu menuTitle : menuTitles) {
            this.add(menuTitle);
        }
    }

    public void addMenuListener(ActionListener menuListener) {
        for (JMenu menu : menuTitles) {
            for (int i = 0; i < menu.getItemCount(); i++) {
                JMenuItem menuItem = menu.getItem(i);
                if (menuItem != null) {
                    menuItem.addActionListener(menuListener);
                }
            }
        }
    }

    private JMenu createMenu(String... params) {
        JMenu menu = new JMenu(params[0]);

        for (int i = 1; i < params.length; i += 2) {
            JMenuItem menuItem = new JMenuItem(params[i]);
            menuItem.setActionCommand(params[i + 1]);
            menu.add(menuItem);
        }

        return menu;
    }
}