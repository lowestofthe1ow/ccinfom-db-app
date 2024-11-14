package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TextFieldsUI;

/* TODO: What is this for lol */
@SuppressWarnings("serial")

public class HireStaffUI extends TextFieldsUI {
    public HireStaffUI() {
        super("Hire staff");
        this.setNext("dialog/hire_staff/select_position");

        addForms("First name: ", "Last name: ", "Contact number: ");
        addButtons("Confirm");
        setButtonActionCommands("button/next");
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