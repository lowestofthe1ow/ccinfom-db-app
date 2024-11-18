package BocchiTheGUI.components.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JLabel;

import BocchiTheGUI.components.abs.PaneUI;
import BocchiTheGUI.interfaces.DataLoadable;

public class PerformerRevenueTab extends PaneUI implements DataLoadable {
    private Object[][] sqlData;
    private ArrayList<JLabel> labels;

    public PerformerRevenueTab(Object[][] sqlData) {
        super("Performer Report Day");
        this.sqlData = sqlData;
        this.labels = new ArrayList<>();
    }

    private void displayItPretty() {
        if (labels.isEmpty()) {
            this.add(new JLabel("There is no data to display for this performer on the given date."));
        }
        for (JLabel a : labels) {
            this.add(a);
        }
    }

    @Override
    public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {
        /* Fetch data from Controller using data from previous dialog window */
        List<Object[]> data = source.apply("sql/performer_report_day", sqlData[0]);

        for (Object[] row : data) {
            for (Object item : row) {
                JLabel label = new JLabel(item.toString());
                labels.add(label);
            }
        }

        displayItPretty();
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        return null;
    }
}
