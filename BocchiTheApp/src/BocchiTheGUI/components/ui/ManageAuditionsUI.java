package BocchiTheGUI.components.ui;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BocchiTheGUI.components.abs.TableSelectionSearchFilterUI;

public class ManageAuditionsUI extends TableSelectionSearchFilterUI {
	private JComboBox<String> comboBox;

	public ManageAuditionsUI() {
		super("Manage Auditions", 1, "ID", "Performer Name", "Submission Link", "Target timeslot",
				"Contact person", "Contact no.", "Status");

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 1));
		panel.setBorder(new EmptyBorder(5, 20, 5, 20));
		panel.add(new JLabel("Only show performances with status: "));

		comboBox = new JComboBox<String>();

		comboBox.addItem("PASSED");
		comboBox.addItem("PENDING");
		comboBox.addItem("REJECTED");

		comboBox.setSelectedItem("PENDING");
		filterTable();

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				filterTable();
			}
		});

		panel.add(comboBox);
		this.add(panel);

		this.addButtons("Accept", "Reject");
		setButtonActionCommands("button/sql/accept_audition", "button/sql/reject_audition");
	}

	@Override
	protected void filterTable() {
		String searchText = searchField.getText().toLowerCase();

		/* Reset the table model */
		activeTableModel.setRowCount(0);

		for (Object[] row : tableRows) {
			/* TODO: Change search column based on mouse click */
			String rowString = (String) row[searchColumnIndex];
			if (rowString.toLowerCase().contains(searchText) && row[6].equals(comboBox.getSelectedItem())) {
				activeTableModel.addRow(row);
			}
		}

		table.clearSelection();
	}
}