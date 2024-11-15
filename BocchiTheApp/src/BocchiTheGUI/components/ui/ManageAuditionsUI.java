package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class ManageAuditionsUI extends TableSelectionUI {
	public ManageAuditionsUI() {
		super("Manage Auditions", 1, "ID", "Performer Name", "Submission Link", "Target timeslot",
				"Contact person", "Contact no.");

		this.addButtons("Accept", "Reject");
		setButtonActionCommands("button/sql/accept_audition", "button/sql/reject_audition");
	}
}