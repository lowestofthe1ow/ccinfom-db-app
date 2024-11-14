package BocchiTheGUI.components.ui;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class AuditionSelectionUI extends TableSelectionUI {
	public AuditionSelectionUI() {
		super("Audition Selection", 1, "ID", "Performer Name", "Submission Link", "Target date", "Target time",
				"Contact person", "Contact no.");
		super.addButtons("Accept", "Reject");
		setButtonActionCommands("accept_audition", "reject_audition");
	}
}