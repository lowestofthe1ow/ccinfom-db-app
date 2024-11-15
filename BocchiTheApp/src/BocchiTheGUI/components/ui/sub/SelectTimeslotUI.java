package BocchiTheGUI.components.ui.sub;

import java.util.ArrayList;
import java.util.Collections;

import BocchiTheGUI.components.abs.TableSelectionDateFilterUI;

public class SelectTimeslotUI extends TableSelectionDateFilterUI {
    private Object[][] sqlData;

    public SelectTimeslotUI(Object[][] sqlData) {
        super("Add audition", 1, "ID", "Start", "End");
        this.setNext("dialog/add_audition/select_timeslot/input_submission");

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/next");

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
