package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class RemoveStaffUI extends TableSelectionUI {
    public RemoveStaffUI() {
        super("Remove staff", "ID", "Name", "Contact no.", "Current position", "Salary");

        this.setLoadDataCommand("sql/get_active_staff");

        this.addSearchBoxFilter("Filter by staff name", 1);

        this.addButtons("Remove");
        this.setButtonActionCommands("button/sql/remove_staff");
    }

    @Override
    public String getLoadFailureMessage() {
        return "No staff data found";
    }
}
