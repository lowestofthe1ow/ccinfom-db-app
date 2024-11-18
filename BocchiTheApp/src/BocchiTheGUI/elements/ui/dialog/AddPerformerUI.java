package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TextFieldsUI;

public class AddPerformerUI extends TextFieldsUI {
    public AddPerformerUI() {
        super("Add performer");

        addForms("Performer name", "Contact person's first name", "Contact person's last name", "Contact number");
        addButtons("Confirm");
        setButtonActionCommands("button/sql/add_performer");
        addTerminatingCommands("button/sql/add_performer");
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = { {
                /* TODO: Data type validation */
                this.formItems.get(0).getText(),
                this.formItems.get(1).getText(),
                this.formItems.get(2).getText(),
                this.formItems.get(3).getText()
        } };
        return retval;
    }
}
