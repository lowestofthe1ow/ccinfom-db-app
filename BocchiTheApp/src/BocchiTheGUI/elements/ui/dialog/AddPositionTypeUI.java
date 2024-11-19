package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TextFieldsUI;

public class AddPositionTypeUI extends TextFieldsUI {
    public AddPositionTypeUI(String root) {
        super("Add position type");

        addForms("Position name", "Salary");
        addButtons("Confirm");
        setButtonActionCommands("button/sql/add_position_type");
        addTerminatingCommands("button/sql/add_position_type");
        setRoot(root);
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = { {
                /* TODO: Data type validation */
                this.formItems.get(0).getText(),
                this.formItems.get(1).getText(),
        } };
        return retval;
    }
}
