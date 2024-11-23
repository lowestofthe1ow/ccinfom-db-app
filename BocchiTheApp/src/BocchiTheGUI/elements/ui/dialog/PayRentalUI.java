package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class PayRentalUI extends TableSelectionUI {
    public PayRentalUI() {
        super("Pay Rental", "ID", "Equipment Name", "Performer Name",
            "Start Date", "End Date", "Payment Status");
            this.setLoadDataCommand("sql/get_unpaid_rentals");
        
        this.addSearchBoxFilter("Filter by equipment name", 1);
        this.addSearchBoxFilter("Filter by performer name", 2);

        this.addButtons("Pay rental");
        this.setButtonActionCommands("button/sql/pay_rental");
    }

    @Override
    public boolean allowEmptyDatasets() {
        return true;
    }
    
    @Override
    public String getLoadFailureMessage() {
        return "No Unpaid rental data found";
    }
}
