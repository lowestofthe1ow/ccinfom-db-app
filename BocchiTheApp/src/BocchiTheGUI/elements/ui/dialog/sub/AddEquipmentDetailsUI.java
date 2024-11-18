package BocchiTheGUI.components.ui.sub;

import BocchiTheGUI.components.abs.TextFieldsUI;

public class AddEquipmentDetailsUI extends TextFieldsUI {
    private Object[][] sqlData;

    public AddEquipmentDetailsUI(Object[][] sqlData) {
        super("Add equipment");

        this.sqlData = sqlData;

        addForms("Equipment name", "Rental fee");
        addButtons("Confirm");
        setRoot("dialog/add_equipment");
        setButtonActionCommands("button/sql/add_equipment");
        addTerminatingCommands("button/sql/add_equipment");
    }

    /**
     * {@inheritDoc} Contains only the parameters for a single query, as multiple
     * simultaneous hires are not allowed.
     */
    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = { {
                this.sqlData[0][0],
                this.formItems.get(0).getText(),
                this.formItems.get(1).getText(),
        } };
        return retval;
    }
}
