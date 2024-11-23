package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;


public class UpdateEquipmentStatusUI extends TableSelectionUI {
	
	public UpdateEquipmentStatusUI(){
		super("Update Equipment Status", "ID", "Equipment Name", "Type", "Status");
		
        this.setLoadDataCommand("sql/get_equipment");

        this.addSearchBoxFilter("Filter by equipment Name", 1);
        this.addSearchBoxFilter("Filter by equipment type", 2);
        this.addComboBoxFilter("Filter by status", 3, "UNDAMAGED", "MIN_DMG", "MAJ_DMG", "MISSING");
        
        this.addButtons("Update");
        
        this.setButtonActionCommands("button/next/change_equipment_status");
	}
	
	@Override
    public String getLoadFailureMessage() {
        return "No equipment data found";
    }
}
