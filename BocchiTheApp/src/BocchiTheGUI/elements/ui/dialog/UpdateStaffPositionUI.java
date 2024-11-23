package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class UpdateStaffPositionUI extends TableSelectionUI {
    public UpdateStaffPositionUI() {
        super("Update staff position", "ID", "Name", "Contact no.", "Current position", "Salary");

        this.setLoadDataCommand("sql/get_staff");

        this.addSearchBoxFilter("Filter by staff name", 1);

        this.addButtons("Update");
        this.setButtonActionCommands("button/next/select_staff_position");
    }

    @Override
    public String getLoadFailureMessage() {
        return "No staff data found";
    }
}
