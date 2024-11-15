package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionSearchFilterUI;

public class AddEquipmentUI extends TableSelectionSearchFilterUI {
    public AddEquipmentUI() {
        super("Add equipment", 1, "ID", "Equipment type");

        this.addButtons("Confirm", "Add type");
        this.setButtonActionCommands(
                "button/next/add_equipment_details",
                "button/next/add_equipment_type");
    }
}
