package BocchiTheGUI.elements.ui.dialog.sub;

public class SelectUpdatedStaffPositionUI extends SelectStaffPositionUI {
    public SelectUpdatedStaffPositionUI(Object[][] sqlData) {
        super(sqlData);
        this.setButtonActionCommands("button/sql/add_position");
        this.addTerminatingCommands("button/sql/add_position");
        this.setRoot("dialog/update_staff_position");
    }
}
