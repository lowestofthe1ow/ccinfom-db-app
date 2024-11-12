package BocchiTheGUI;

import java.util.ArrayList;

public class AuditionSelectionUI extends TableSelectionUI {

	AuditionSelectionUI() {

		super("Audition Selection", "ID", "Performer Name", "Submission Link");
		super.addButtons("Accept");
		super.addButtons("Reject");
		setButtonActionCommands("accept_audition", "reject_audition");
	}

	@Override
	public Object[][] getSQLParameterInputs() {
		int[] selectedRowIndices = table.getSelectedRows();

		if (selectedRowIndices.length == 0) {
			return null;
		}

		ArrayList<Object[]> retval = new ArrayList<>();

		for (int selectedRowIndex : selectedRowIndices) {
			Object[] val = { table.getValueAt(selectedRowIndex, 0) };

			if (val[0] instanceof Integer) {
				retval.add(val);
			} else {
				throw new IllegalArgumentException("Selected row(s) does not have an Integer ID.");
			}
		}

		return retval.toArray(new Object[retval.size()][]);
	}
}
