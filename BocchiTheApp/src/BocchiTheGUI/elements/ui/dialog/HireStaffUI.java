package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abs.TextFieldsUI;

/* TODO: What is this for lol */
@SuppressWarnings("serial")

public class HireStaffUI extends TextFieldsUI {
    public HireStaffUI() {
        super("Hire staff");

        addForms("First name: ", "Last name: ", "Contact number: ");
        addButtons("Confirm");
        setButtonActionCommands("button/next/select_staff_position");
    }

    private String getFirstName() {
        return this.formItems.get(0).getText();
    }

    private String getLastName() {
        return this.formItems.get(1).getText();
    }

    private Long getContactNo() {
        return Long.parseLong(this.formItems.get(2).getText());
    }

    /**
     * {@inheritDoc} Contains only the parameters for a single query, as multiple
     * simultaneous hires are not allowed.
     */
    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = { {
                getFirstName(),
                getLastName(),
                getContactNo()
        } };
        return retval;
    }
}