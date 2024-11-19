package BocchiTheGUI.elements.ui.dialog.sub;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class ViewAssignedStaffUI extends TableSelectionUI {
    Object[][] sqlData;

    public ViewAssignedStaffUI(Object[][] sqlData) {
        super("Assigned staff", "ID", "Name", "Contact no.", "Current position", "Salary");
        this.sqlData = sqlData;
        this.setRoot("dialog/assign_staff");
        this.setLoadDataCommand("sql/view_assigned_staff");
        this.setLoadDataParams(sqlData[0][0].toString());
        this.addSearchBoxFilter("Filter by staff name", 1);
    }
}
