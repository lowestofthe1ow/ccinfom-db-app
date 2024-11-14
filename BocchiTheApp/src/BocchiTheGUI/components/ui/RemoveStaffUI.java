package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class RemoveStaffUI extends TableSelectionUI {
    public RemoveStaffUI() {
        super("Remove staff", 1, "ID", "Name", "Contact no.", "Current position");
        this.addButtons("Remove");
        this.setButtonActionCommands("remove_staff");
    }
}
