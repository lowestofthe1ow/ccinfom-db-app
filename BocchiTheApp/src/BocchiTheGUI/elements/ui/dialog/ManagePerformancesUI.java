package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class ManagePerformancesUI extends TableSelectionUI {
    public ManagePerformancesUI() {
        super("Manage performances", "ID", "Performer", "Timeslot", "Status", "Quota");

        this.setLoadDataCommand("sql/get_performances");

        this.addSearchBoxFilter("Filter by performer name", 1);
        this.addDatePickerFilter("Filter by timeslot", 2);
        this.addComboBoxFilter("Filter by status", 3, "PENDING", "COMPLETE", "CANCELLED");

        this.addButtons("Record revenue", "Cancel performance");
        this.setButtonActionCommands("button/next/record_revenue", "button/sql/cancel_performance");
    }

    @Override
    public String getLoadFailureMessage() {
        return "No Performance data found";
    }
}
