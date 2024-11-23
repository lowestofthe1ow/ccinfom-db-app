package BocchiTheGUI.elements.ui.dialog.sub;

import java.util.ArrayList;
import java.util.List;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class RemoveAssignedPerformances extends TableSelectionUI{
	 private Object[][] sqlData;
	 
	public RemoveAssignedPerformances(Object[][] sqlData) {
		super("Assigned performances", "ID", "Performer name", "Start timestamp", "Status");
        this.setLoadDataCommand("sql/view_assigned_performances");
        this.setRoot("dialog/remove_staff_assignment");
		this.sqlData = sqlData;
		this.addButtons("Remove");
		this.setButtonActionCommands("button/sql/remove_staff_assignment");
	}
	
	 @Override
	    public Object[][] getSQLParameterInputs() {
	        List<Object[]> retval_list = new ArrayList<>();

	        for (Object[] row : super.getSQLParameterInputs()) {
	            Object[] temp = {
	                    row[0],
	                    sqlData[0][0]
	            };
	            retval_list.add(temp);
	        }

	        return retval_list.toArray(new Object[retval_list.size()][]);
	    }
	 
	 @Override
	    public String getLoadFailureMessage() {
	        return "No performance data found";
	    }
}
