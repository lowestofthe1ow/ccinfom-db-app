package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class ManagePerformancesUI extends TableSelectionUI {
    public ManagePerformancesUI() {
        super("Manage performances", "ID", "Performer", "Timeslot", "Status");

        this.addSearchBoxFilter("Filter by performer name", 1);
        this.addDatePickerFilter("Filter by timeslot", 2);
        this.addComboBoxFilter("Filter by status", 3, "PENDING", "COMPLETE", "CANCELLED");

        this.addButtons("Record revenue", "Cancel performance");
        this.setButtonActionCommands("button/next/record_revenue", "button/sql/cancel_performance");
    }
}
