package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class UpdateStaffPositionUI extends TableSelectionUI {
    public UpdateStaffPositionUI() {
        super("Update staff position", "ID", "Name", "Contact no.", "Current position", "Salary");

        this.addSearchBoxFilter("Filter by staff name", 1);

        this.addButtons("Update");
        this.setButtonActionCommands("button/next/select_position");
    }
}
