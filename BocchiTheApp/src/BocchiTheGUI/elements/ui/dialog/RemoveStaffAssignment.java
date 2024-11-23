package BocchiTheGUI.elements.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;


public class RemoveStaffAssignment extends TableSelectionUI{

	public RemoveStaffAssignment() {
		super("Remove Staff Assignment", "Staff ID", "Staff Name");
		this.setLoadDataCommand("sql/get_active_staff");

        this.addSearchBoxFilter("Filter by staff name", 1);

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/next/view_assigned_performances");

	}

}
