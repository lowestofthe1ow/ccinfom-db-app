package BocchiTheGUI.elements.ui.dialog.sub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import BocchiTheGUI.elements.abstracts.TextFieldsUI;

public class RecordRevenueUI extends TextFieldsUI {
    private Object[][] sqlData;

    public RecordRevenueUI(Object[][] sqlData) {
        super("Record revenue");
        this.sqlData = sqlData;

        addForms("Ticket price", "Tickets sold", "Cut percent");
        addButtons("Confirm");
        setRoot("dialog/manage_performances");
        setButtonActionCommands("button/sql/record_performance_revenue");
        addTerminatingCommands("button/sql/record_performance_revenue");
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        List<Object> params = new ArrayList<>();

        Collections.addAll(params, sqlData[0]);
        params.add(this.formItems.get(0).getText());
        params.add(this.formItems.get(1).getText());
        params.add(this.formItems.get(2).getText());

        Object[][] retval = {
                params.toArray()
        };

        return retval;
    }
}
