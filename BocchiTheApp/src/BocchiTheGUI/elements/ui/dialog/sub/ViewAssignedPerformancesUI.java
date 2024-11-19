package BocchiTheGUI.elements.ui.dialog.sub;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class ViewAssignedPerformancesUI extends TableSelectionUI {
    public ViewAssignedPerformancesUI(Object[][] sqlData) {
        super("Assigned performances", "ID", "Performer name", "Start timestamp", "Status");
        this.setLoadDataCommand("sql/view_assigned_performances");
        this.setLoadDataParams(sqlData[0][0].toString());
    }
}
