package BocchiTheGUI.elements.ui.tab;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class ScheduleTab extends TableSelectionUI {
    public ScheduleTab(Object[][] sqlData) {
        super("Schedule", "Performer Name", "Day", "Start Time", "End Time");
        this.setLoadDataCommand("sql/get_schedule");
        this.addSearchBoxFilter("Sort by performer name", 0);
    }
    
    @Override
    public Object[][] getSQLParameterInputs() {
        return null;
    }

    @Override
    public boolean allowEmptyDatasets() {
        return false;
    }

    @Override
    public String getLoadFailureMessage() {
        return "There are no recorded performances this week.";
    }
}
