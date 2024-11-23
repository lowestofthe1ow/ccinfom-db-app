package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class AddAuditionUI extends TableSelectionUI {
    public AddAuditionUI() {
        super("Add audition", "ID", "Performer", "Contact person", "Contact number");

        this.setLoadDataCommand("sql/get_performers");

        this.addSearchBoxFilter("Filter by performer name", 1);
        this.addSearchBoxFilter("Filter by contact person name", 2);

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/next/select_timeslot");
    }
    @Override
    public String getLoadFailureMessage() {
        return "No Performer data found";
    }
}