package BocchiTheGUI.components.ui.sub;

import BocchiTheGUI.components.abs.TextFieldsUI;

public class AddEquipmentTypeUI extends TextFieldsUI {
    public AddEquipmentTypeUI() {
        super("Add equipment type");

        addForms("Equipment type");
        addButtons("Confirm");
        setRoot("dialog/add_equipment");
        setButtonActionCommands("button/sql/add_equipment_type");
        addTerminatingCommands("button/sql/add_equipment_type");
    }

    /**
     * {@inheritDoc} Contains only the parameters for a single query, as multiple
     * simultaneous hires are not allowed.
     */
    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = { {
                this.formItems.get(0).getText()
        } };
        return retval;
    }
}
