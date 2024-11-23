package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class RentEquipmentUI extends TableSelectionUI {
    public RentEquipmentUI() {
        super("Rent equipment", "ID", "Equipment name", "Equipment Type", "Rental fee");

        this.setLoadDataCommand("sql/get_undamaged_equipment");
        this.addSearchBoxFilter("Filter by equipment name", 1);
        this.addSearchBoxFilter("Filter by equipment type", 2);

        this.addButtons("Rent equipment");
        this.setButtonActionCommands("button/next/select_performer");
    }

    @Override
    public String getLoadFailureMessage() {
        return "No equipment data found";
    }
}
