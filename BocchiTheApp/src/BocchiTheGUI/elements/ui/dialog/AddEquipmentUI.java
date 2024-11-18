package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class AddEquipmentUI extends TableSelectionUI {
    public AddEquipmentUI() {
        super("Add equipment", "ID", "Equipment type");

        this.setLoadDataCommand("sql/get_equipment_types");

        this.addSearchBoxFilter("Filter by equipment type", 1);

        this.addButtons("Confirm", "Add type");
        this.setButtonActionCommands(
                "button/next/add_equipment_details",
                "button/next/add_equipment_type");
    }
}
