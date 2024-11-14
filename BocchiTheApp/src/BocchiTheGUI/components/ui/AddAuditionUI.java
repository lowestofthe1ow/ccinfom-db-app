package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class AddAuditionUI extends TableSelectionUI {
    public AddAuditionUI() {
        super("Add audition", 1, "ID", "Performer", "Contact person", "Contact number");
        this.setNext("dialog/add_audition/select_timeslot");

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/next");
    }
}
