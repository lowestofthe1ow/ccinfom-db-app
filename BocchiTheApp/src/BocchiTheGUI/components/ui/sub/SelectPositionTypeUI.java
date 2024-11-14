package BocchiTheGUI.components.ui.sub;

import java.util.ArrayList;
import java.util.Collections;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class SelectPositionTypeUI extends TableSelectionUI {
    private Object[][] sqlData;

    public SelectPositionTypeUI(String buttonName, String buttonCommand, String rootName, Object[][] sqlData) {
        super("Select staff position", 1, "ID", "Position", "Salary");
        this.addButtons(buttonName);
        this.setButtonActionCommands(buttonCommand);
        this.addTerminatingCommands(buttonCommand);
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
