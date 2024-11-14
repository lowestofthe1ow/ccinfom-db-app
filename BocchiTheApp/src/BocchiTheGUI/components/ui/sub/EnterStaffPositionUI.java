package BocchiTheGUI.components.ui.sub;

import BocchiTheGUI.components.abs.TextFieldsUI;

public class EnterStaffPositionUI extends TextFieldsUI {
    private Object[][] sqlData;

    public EnterStaffPositionUI(Object[][] sqlData) {
        super("Enter new staff position for staff ID " + sqlData[0][0]);

        /* Load SQL data from previous window */
        this.sqlData = sqlData;
        /* This sub-dialog is rooted in update_staff_position */
        setRoot("update_staff_position");

        addForms("New position", "New salary");
        addButtons("Confirm");

        /* TODO: Rename this command, it's pretty confusing */
        setButtonActionCommands("add_position");
        addTerminatingCommands("add_position");

    }

    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = { {
                this.sqlData[0][0],
                this.formItems.get(0).getText(),
                this.formItems.get(1).getText()
        } };

        return retval;
    }
}
