package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionSearchFilterUI;

public class RemoveStaffUI extends TableSelectionSearchFilterUI {
    public RemoveStaffUI() {
        super("Remove staff", 1, "ID", "Name", "Contact no.", "Current position", "Salary");

        this.addButtons("Remove");
        this.setButtonActionCommands("button/sql/remove_staff");
    }
}
