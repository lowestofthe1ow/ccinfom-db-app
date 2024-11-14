package BocchiTheGUI.components.ui.sub;

import java.util.ArrayList;
import java.util.Collections;

import BocchiTheGUI.components.abs.TextFieldsUI;

public class InputSubmissionUI extends TextFieldsUI {
    private Object[][] sqlData;

    public InputSubmissionUI(Object[][] sqlData) {
        super("Input submission URL");

        this.addForms("Submission URL");
        this.addButtons("Confirm");
        this.setButtonActionCommands("button/sql/sched_audition");
        this.addTerminatingCommands("button/sql/sched_audition");

        this.sqlData = sqlData;
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        ArrayList<Object> params = new ArrayList<>();

        Collections.addAll(params, sqlData[0]);
        params.add(this.formItems.get(0).getText());

        Object[][] retval = {
                params.toArray()
        };

        return retval;
    }
}
