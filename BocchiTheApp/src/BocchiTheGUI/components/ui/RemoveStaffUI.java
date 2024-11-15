package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class RemoveStaffUI extends TableSelectionUI {
    public RemoveStaffUI() {
        super("Remove staff", "ID", "Name", "Contact no.", "Current position", "Salary");

        this.addSearchBoxFilter("Filter by staff name", 1);

        this.addButtons("Remove");
        this.setButtonActionCommands("button/sql/remove_staff");
    }
}
