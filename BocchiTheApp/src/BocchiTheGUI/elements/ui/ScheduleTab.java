package BocchiTheGUI.elements.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

import BocchiTheGUI.elements.abs.PaneUI;
import BocchiTheGUI.interfaces.DataLoadable;

public class ScheduleTab extends PaneUI implements DataLoadable {
    private Object[][] sqlData;
    private ArrayList<JLabel> labels;

    public ScheduleTab(Object[][] sqlData) {
        super("Schedule");
        this.sqlData = sqlData;
        this.labels = new ArrayList<>();

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
}
