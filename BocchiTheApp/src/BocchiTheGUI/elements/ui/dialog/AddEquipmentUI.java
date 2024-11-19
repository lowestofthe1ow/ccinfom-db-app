package BocchiTheGUI.elements.ui.dialog;

import java.util.List;
import java.util.function.BiFunction;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

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

    @Override
    public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {
        /* We're allowing this component to load empty data */
        try {
            super.loadData(source);
        } catch (Exception e) {
            /* TODO: Put a warning in here idk */
        }
    }
}
