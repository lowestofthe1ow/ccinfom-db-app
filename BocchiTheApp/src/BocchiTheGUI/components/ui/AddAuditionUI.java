package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionSearchFilterUI;

public class AddAuditionUI extends TableSelectionSearchFilterUI {
    public AddAuditionUI() {
        super("Add audition", 1, "ID", "Performer", "Contact person", "Contact number");

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/next/select_timeslot");
    }
}
