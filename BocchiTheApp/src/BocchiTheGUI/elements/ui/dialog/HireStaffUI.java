package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TextFieldsUI;

/* TODO: What is this for lol */
@SuppressWarnings("serial")

public class HireStaffUI extends TextFieldsUI {
    public HireStaffUI() {
        super("Hire staff");

        addForms("First name: ", "Last name: ", "Contact number: ");
        addButtons("Confirm");
        setButtonActionCommands("button/next/select_staff_position");
    }

    /**
     * {@inheritDoc} Contains only the parameters for a single query, as multiple
     * simultaneous hires are not allowed.
     */
    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = { {
            this.formItems.get(0).getText(),
            this.formItems.get(1).getText(),
            this.formItems.get(2).getText()
        } };
        return retval;
    }
}