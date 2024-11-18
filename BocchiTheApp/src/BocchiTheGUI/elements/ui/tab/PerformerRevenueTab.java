package BocchiTheGUI.elements.ui.tab;

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

import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.XChartPanel;

import BocchiTheGUI.elements.abs.PaneUI;
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

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        String content = "<html>"
                + "<body style='font-family: Arial, sans-serif;'>"
                + "<h1 style='color: #3366cc;'>Sales Information for " + labels.get(0).getText() + " on "
                + labels.get(1).getText() + "</h1>"
                + "<p style='font-size: 12px;'>"
                + "<b>Sales:</b> " + labels.get(2).getText() + "<br>"
                + "<b>Performer Profit:</b> " + labels.get(3).getText() + "<br>"
                + "<b>Performer Debt:</b> " + labels.get(4).getText() + "<br>"
                + "<b>Livehouse Profit:</b> " + labels.get(5).getText()
                + "</p>"
                + "</body>"
                + "</html>";

        JEditorPane editorPane = new JEditorPane("text/html", content);
        editorPane.setEditable(false);
        editorPane.setPreferredSize(new Dimension(600, 200));
        editorPane.setBackground(Color.white);

        panel.add(editorPane);

        XChartPanel<PieChart> chartPanel = new XChartPanel<>(createPieChart());

        panel.add(chartPanel);

        add(panel);
    }

    private PieChart createPieChart() {

        PieChart chart = new PieChartBuilder().width(400).height(400).build();
        Map<String, Double> data = new HashMap<>();
        data.put("Sales on Day", Double.parseDouble(labels.get(2).getText()));
        data.put("Performer Profit On Day", Double.parseDouble(labels.get(3).getText()));
        data.put("Performer Debt On Day", Double.parseDouble(labels.get(4).getText()));
        data.put("LiveHouse Profit On Day", Double.parseDouble(labels.get(5).getText()));

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            chart.addSeries(entry.getKey(), entry.getValue());
        }

        return chart;
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
