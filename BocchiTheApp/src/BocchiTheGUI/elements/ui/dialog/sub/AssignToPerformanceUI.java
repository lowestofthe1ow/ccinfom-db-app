package BocchiTheGUI.elements.ui.dialog.sub;

import java.util.ArrayList;
import java.util.List;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class AssignToPerformanceUI extends TableSelectionUI {
    private Object[][] sqlData;

    public AssignToPerformanceUI(Object[][] sqlData) {
        super("Assign to a performance", "ID", "Name", "Contact no.", "Current position", "Salary");
        this.sqlData = sqlData;
        this.setLoadDataCommand("sql/get_active_staff");
        this.setRoot("dialog/assign_staff");

        this.addSearchBoxFilter("Filter by staff name", 1);
        this.addButtons("Assign to performance", "View assigned performances");
        this.setButtonActionCommands("button/sql/assign_staff", "button/next/view_assigned_performances");
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        List<Object[]> retval_list = new ArrayList<>();

        for (Object[] row : super.getSQLParameterInputs()) {
            Object[] temp = {
                    row[0],
                    sqlData[0][0]
            };
            retval_list.add(temp);
        }

        return retval_list.toArray(new Object[retval_list.size()][]);
    }

    @Override
    public String getLoadFailureMessage() {
        return "No staff data found";
    }
}
