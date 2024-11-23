package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TextFieldsUI;

public class AddPerformerUI extends TextFieldsUI {
    public AddPerformerUI() {
        super("Add performer");

        addForms("Performer name", "Contact person's first name", "Contact person's last name", "Contact number");
        addButtons("Confirm");
        setButtonActionCommands("button/sql/add_performer");
        addTerminatingCommands("button/sql/add_performer");
    }
}
