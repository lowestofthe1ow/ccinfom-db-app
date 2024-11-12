package BocchiTheGUI.components.ui;

import java.util.ArrayList;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class AuditionSelectionUI extends TableSelectionUI {
	public AuditionSelectionUI() {
		super("Audition Selection", 1, "ID", "Performer Name", "Submission Link", "Target date", "Target time");
		super.addButtons("Accept", "Reject");
		setButtonActionCommands("accept_audition", "reject_audition");
	}

	/**
	 * {@inheritDoc} Each query represents a row included in the table selection
	 * (which may include multiple rows).
	 */
	@Override
	public Object[][] getSQLParameterInputs() {
		/* Get selected rows and create an empty ArrayList */
		int[] selectedRowIndices = table.getSelectedRows();
		ArrayList<Object[]> retval = new ArrayList<>();

		for (int selectedRowIndex : selectedRowIndices) {
			/* Create an array containing only the audition ID */
			Object[] val = { table.getValueAt(selectedRowIndex, 0) };

			/* Verify that the ID is an integer */
			if (val[0] instanceof Integer) {
				retval.add(val);
			} else {
				throw new IllegalArgumentException("Selected row(s) does not have an Integer ID.");
			}
		}

		/*
		 * Return an array of singleton array (each singleton is processed as the
		 * parameter passed to the SQL stored procedure)
		 */
		return retval.toArray(new Object[retval.size()][]);
	}
}