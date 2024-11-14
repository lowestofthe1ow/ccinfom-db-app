package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class UpdateStaffPositionUI extends TableSelectionUI {
    public UpdateStaffPositionUI() {
        super("Update staff position", 1, "ID", "Name", "Contact no.", "Current position");
        this.setNext("add_position");

        this.addButtons("Update");
        this.setButtonActionCommands("update_staff_position");
        this.addTerminatingCommands("update_staff_position");
    }
}
