package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class PerformerRevenueUI extends TableSelectionUI {
    public PerformerRevenueUI() {
        super("Generate performer revenue summary", "ID", "Performer", "Contact person", "Contact number");

        this.setLoadDataCommand("sql/get_performers");

        this.addSearchBoxFilter("Filter by performer name", 1);
        this.addSearchBoxFilter("Filter by contact person name", 2);

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/report/performer_revenue");
    }
}
