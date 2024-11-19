package BocchiTheGUI.elements.ui.dialog.sub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class SelectStaffPositionUI extends TableSelectionUI {
    private Object[][] sqlData;

    public SelectStaffPositionUI(String actionCommand, String rootName, Object[][] sqlData) {
        super("Select staff position", "ID", "Position", "Salary");

        this.setLoadDataCommand("sql/get_positions");

        this.addSearchBoxFilter("Filter by position name", 1);

        this.addButtons("Confirm");
        this.setButtonActionCommands(actionCommand);
        this.addTerminatingCommands(actionCommand);
        this.setRoot(rootName);
        this.sqlData = sqlData;
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        List<Object> params = new ArrayList<>();

        Collections.addAll(params, sqlData[0]);
        params.add(super.getSQLParameterInputs()[0][0]);

        Object[][] retval = {
                params.toArray()
        };

        return retval;
    }

    /*
     * TODO: We allow empty data here because we're planning to add Add Positions to
     * this dialog
     */
    @Override
    public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {
        /* We're allowing this component to load empty data */
        try {
            super.loadData(source);
        } catch (Exception e) {
            /* TODO: Put a warning in here idk */
        }
    }
}
