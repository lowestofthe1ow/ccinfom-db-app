package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionSearchFilterUI;

public class UpdateStaffPositionUI extends TableSelectionSearchFilterUI {
    public UpdateStaffPositionUI() {
        super("Update staff position", 1, "ID", "Name", "Contact no.", "Current position", "Salary");
        this.setNext("dialog/update_staff_position/select_position");

        this.addButtons("Update");
        this.setButtonActionCommands("button/next");
    }
}
