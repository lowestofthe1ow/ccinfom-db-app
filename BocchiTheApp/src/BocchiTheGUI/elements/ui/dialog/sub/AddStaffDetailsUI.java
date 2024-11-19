package BocchiTheGUI.elements.ui.dialog.sub;

import BocchiTheGUI.elements.abstracts.TextFieldsUI;

/* TODO: What is this for lol */
@SuppressWarnings("serial")

public class AddStaffDetailsUI extends TextFieldsUI {
    Object[][] sqlData;

    public AddStaffDetailsUI(Object[][] sqlData) {
        super("Hire staff");
        this.sqlData = sqlData;
        addForms("First name: ", "Last name: ", "Contact number: ");
        addButtons("Confirm");
        setButtonActionCommands("button/sql/hire");
        addTerminatingCommands("button/sql/hire");
        setRoot("dialog/hire_staff");
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
                getContactNo(),
                this.sqlData[0][0]
        } };
        return retval;
    }
}