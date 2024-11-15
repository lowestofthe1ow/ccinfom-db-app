package BocchiTheGUI.components.ui.sub;

import java.util.ArrayList;
import java.util.Collections;

import BocchiTheGUI.components.abs.TableSelectionDateFilterUI;

public class SelectTimeslotUI extends TableSelectionDateFilterUI {
    private Object[][] sqlData;

    public SelectTimeslotUI(Object[][] sqlData) {
        super("Select target timeslot", 1, "ID", "Start", "End");

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
