package BocchiTheGUI.elements.ui.dialog.sub;

import java.util.ArrayList;
import java.util.List;

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

        this.addButtons("Remove");
        this.setButtonActionCommands("button/sql/remove_staff_assignment");
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        List<Object[]> retval = new ArrayList<>();

        for (Object[] row : super.getSQLParameterInputs()) {
            Object[] temp = {
                row[0], 
                sqlData[0][0].toString()
            };
            retval.add(temp);
        }

        return retval.toArray(new Object[retval.size()][]);
    }

    @Override
    public boolean allowEmptyDatasets() {
        return true;
    }
}
