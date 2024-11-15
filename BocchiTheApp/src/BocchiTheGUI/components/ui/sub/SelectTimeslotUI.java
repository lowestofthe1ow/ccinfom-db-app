package BocchiTheGUI.components.ui.sub;

import java.util.ArrayList;
import java.util.Collections;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class SelectTimeslotUI extends TableSelectionUI {
    private Object[][] sqlData;

    public SelectTimeslotUI(Object[][] sqlData) {
        super("Select target timeslot", "ID", "Start", "End");

        this.addDatePickerFilter("Filter by start date", 1);
        this.addDatePickerFilter("Filter by end date", 2);

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/next/input_submission");

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
