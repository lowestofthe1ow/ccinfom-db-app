package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionSearchFilterUI;

public class UpdateStaffPositionUI extends TableSelectionSearchFilterUI {
    public UpdateStaffPositionUI() {
        super("Update staff position", 1, "ID", "Name", "Contact no.", "Current position", "Salary");

        this.addButtons("Update");
        this.setButtonActionCommands("button/next/select_position");
    }
}
