package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class UpdateStaffPositionUI extends TableSelectionUI {
    public UpdateStaffPositionUI() {
        super("Update staff position", 1, "ID", "Name", "Contact no.", "Current position", "Salary");
        this.setNext("update_staff_position_select_position");

        this.addButtons("Update");
        this.setButtonActionCommands("update_staff_position_select_position");
        this.addTerminatingCommands("update_staff_position_select_position");
    }
}
