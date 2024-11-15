package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class ManageAuditionsUI extends TableSelectionUI {
	public ManageAuditionsUI() {
		super("Manage Auditions", "ID", "Performer Name", "Submission Link", "Target timeslot",
				"Contact person", "Contact no.", "Status");

		this.addSearchBoxFilter("Filter by performer name", 1);
		this.addSearchBoxFilter("Filter by contact person name", 4);
		this.addDatePickerFilter("Filter by target timeslot", 3);
		this.addComboBoxFilter("Filter by status", 6,
				"PENDING", "PASSED", "REJECTED");

		this.addButtons("Accept", "Reject");
		setButtonActionCommands("button/sql/accept_audition", "button/sql/reject_audition");
	}
}