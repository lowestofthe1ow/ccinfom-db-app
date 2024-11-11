package BocchiTheGUI;

public class HireStaffUI extends TextFieldsUI {
    public HireStaffUI() {
        super("First name: ", "Last name: ", "Contact number: ", "Staff position: ", "Salary: ");

        addButtons("Confirm");
        setButtonActionCommands("hire");
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
     * {@return an array of objects that represent the parameters to pass to the SQL
     * query} Uses values from the form inputs.
     */
    public Object[] getSQLParameterInputs() {
        Object[] SQLParameters = {
                getFirstName(),
                getLastName(),
                getContactNo(),
                getPositionName(),
                getSalary()
        };

        return SQLParameters;
    }
}
