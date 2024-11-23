package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class AssignStaffUI extends TableSelectionUI {
    public AssignStaffUI() {
        super("Assign staff to a performance", "ID", "Performer", "Timeslot", "Status");

        this.setLoadDataCommand("sql/get_pending_performances");

        this.addSearchBoxFilter("Filter by performer name", 1);
        this.addDatePickerFilter("Filter by timeslot", 2);

        this.addButtons("Assign a staff", "View assigned staff");

        this.setButtonActionCommands("button/next/assign_to_performance", "button/next/view_assigned_staff");
    }

    @Override
    public String getLoadFailureMessage() {
        return "No Pending Performance data found";
    }
}