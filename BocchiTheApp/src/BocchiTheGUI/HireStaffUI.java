package BocchiTheGUI;

public class HireStaffUI extends TextFieldsUI {
    public HireStaffUI() {
        super("First name: ", "Last name: ", "Contact number: ", "Staff position: ", "Salary: ");
    }

    public String getFirstName() {
        return this.formItems.get(0).getText();
    }

    public String getLastName() {
        return this.formItems.get(1).getText();
    }

    public Long getContactNo() {
        return Long.parseLong(this.formItems.get(2).getText());
    }

    public String getPositionName() {
        return this.formItems.get(3).getText();
    }

    public Double getSalary() {
        return Double.parseDouble(this.formItems.get(4).getText());
    }
}
