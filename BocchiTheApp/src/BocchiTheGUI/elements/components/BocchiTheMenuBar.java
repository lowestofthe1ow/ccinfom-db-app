package BocchiTheGUI.elements.components;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class BocchiTheMenuBar extends JMenuBar {
    private List<JMenu> menuTitles;

    public BocchiTheMenuBar() {
        menuTitles = new ArrayList<>();

        menuTitles.add(createMenu("Staff",
                "Hire Staff", "dialog/hire_staff",
                "Remove Staff", "dialog/remove_staff",
                "Update Staff Position", "dialog/update_staff_position",
                "Add Position Type", "dialog/add_position_type",
                "Cancel Staff Assignment", "dialog/remove_staff_assignment"));

        menuTitles.add(createMenu("Audition/Performance",
                "Add Performer", "dialog/add_performer",
                "Add Timeslot", "dialog/add_timeslot",
                "Add Audition", "dialog/add_audition",
                "Manage Auditions", "dialog/manage_auditions",
                "Manage Performances", "dialog/manage_performances",
                "Assign Staff", "dialog/assign_staff"));

        menuTitles.add(createMenu("Equipment",
                "Add Equipment", "dialog/add_equipment",
                "Rent Equipment", "dialog/rent_equipment",
                "Manage rentals", "dialog/manage_rentals",
                "Pay Rental", "dialog/pay_rental",
                "Cancel Rental", "dialog/cancel_rental",
                "Update Equipment Status", "dialog/update_equipment_status"));

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