package BocchiTheGUI.elements.ui.dialog.sub;

import BocchiTheGUI.elements.abstracts.TextFieldsUI;

public class AddEquipmentTypeUI extends TextFieldsUI {
    public AddEquipmentTypeUI() {
        super("Add equipment type");

        addForms("Equipment type");
        addButtons("Confirm");
        setRoot("dialog/add_equipment");
        setButtonActionCommands("button/sql/add_equipment_type");
        addTerminatingCommands("button/sql/add_equipment_type");
    }
}
