package BocchiTheGUI.elements.ui.dialog.sub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import BocchiTheGUI.elements.abs.TextFieldsUI;

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
        List<Object> params = new ArrayList<>();

        Collections.addAll(params, sqlData[0]);
        params.add(this.formItems.get(0).getText());

        Object[][] retval = {
                params.toArray()
        };

        return retval;
    }
}
