package BocchiTheGUI.components.ui.sub;

import java.util.ArrayList;
import java.util.Collections;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class SelectStaffPositionUI extends TableSelectionUI {
    private Object[][] sqlData;

    public SelectStaffPositionUI(String buttonLabel, String actionCommand, String rootName, Object[][] sqlData) {
        super("Select staff position", "ID", "Position", "Salary");

        this.addSearchBoxFilter("Filter by position name", 1);

        this.addButtons(buttonLabel);
        this.setButtonActionCommands(actionCommand);
        this.addTerminatingCommands(actionCommand);
        this.setRoot(rootName);
        this.sqlData = sqlData;
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        ArrayList<Object> params = new ArrayList<>();

        Collections.addAll(params, sqlData[0]);
        params.add(super.getSQLParameterInputs()[0][0]);

        Object[][] retval = {
                params.toArray()
        };

        return retval;
    }
}
