package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class AddAuditionUI extends TableSelectionUI {
    public AddAuditionUI() {
        super("Add audition", "ID", "Performer", "Contact person", "Contact number");

        this.addSearchBoxFilter("Filter by performer name", 1);

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/next/select_timeslot");
    }
}