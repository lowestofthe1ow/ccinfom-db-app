package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class RemoveStaffUI extends TableSelectionUI {
    public RemoveStaffUI() {
        super("Remove staff", 1, "ID", "Name", "Contact no.", "Current position", "Salary");

        this.addButtons("Remove");
        this.setButtonActionCommands("button/sql/remove_staff");
    }
}
