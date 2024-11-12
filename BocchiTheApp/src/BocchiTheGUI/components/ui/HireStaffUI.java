package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TextFieldsUI;

/* TODO: What is this for lol */
@SuppressWarnings("serial")

public class HireStaffUI extends TextFieldsUI {
    public HireStaffUI() {
        super("Hire staff");
        addForms("First name: ", "Last name: ", "Contact number: ", "Staff position: ", "Salary: ");
        addButtons("Confirm");
        setButtonActionCommands("hire");
        addTerminatingCommands("hire");
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

    private String getPositionName() {
        return this.formItems.get(3).getText();
    }

    private Double getSalary() {
        return Double.parseDouble(this.formItems.get(4).getText());
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
                getPositionName(),
                getSalary()
        } };

        return retval;
    }
}