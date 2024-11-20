package BocchiTheGUI.elements.ui.dialog.sub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class SelectTimeslotUI extends TableSelectionUI {
    private Object[][] sqlData;

    public SelectTimeslotUI(Object[][] sqlData) {
        super("Select target timeslot", "ID", "Start", "End");

        this.setLoadDataCommand("sql/get_timeslots");

        this.addDatePickerFilter("Filter by start date", 1);
        this.addDatePickerFilter("Filter by end date", 2);

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/next/input_submission");

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
}
