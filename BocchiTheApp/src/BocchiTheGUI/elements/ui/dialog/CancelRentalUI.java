package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class CancelRentalUI extends TableSelectionUI {
    public CancelRentalUI() {
        super("Cancel Rental", "ID", "Equipment Name", "Performer Name",
            "Start Date", "End Date", "Equipment Status");
            this.setLoadDataCommand("sql/get_equipment_rentals");
        
        this.addSearchBoxFilter("Filter by equipment name", 1);
        this.addSearchBoxFilter("Filter by performer name", 2);

        this.addButtons("Cancel rental");
        this.setButtonActionCommands("button/sql/cancel_rental");
    }
    
    @Override
    public String getLoadFailureMessage() {
        return "No Equipment rental data found";
    }
}
