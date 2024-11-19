package BocchiTheGUI.elements.ui.tab;

import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BocchiTheGUI.elements.abstracts.PaneUI;
import BocchiTheGUI.interfaces.DataLoadable;

public class ScheduleTab extends PaneUI implements DataLoadable {
    /* TODO: Re-add these when they're needed */
    // private Object[][] sqlData;
    // private ArrayList<JLabel> labels;

    public ScheduleTab(Object[][] sqlData) {
        super("Schedule");
        // this.sqlData = sqlData;
        // this.labels = new ArrayList<>();

    }

    @Override
    public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {

        List<Object[]> data = source.apply("sql/get_schedule", null);

        String[] columnNames = { "Performer Name", "Day", "Start Time", "End Time" };

        // Create a table model and populate it with data
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Object[] row : data) {
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);

        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);

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
