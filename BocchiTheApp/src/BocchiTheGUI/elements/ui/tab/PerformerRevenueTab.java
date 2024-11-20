package BocchiTheGUI.elements.ui.tab;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
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

import BocchiTheGUI.elements.abstracts.PaneUI;
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
        /* This should not occur in normal circumstances */
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
                + "<b>Monthly sales:</b> " + labels.get(2).getText() + "<br>"
                + "<b>Monthly performer profit:</b> " + labels.get(3).getText() + "<br>"
                //+ "<b>Monthly unmet sales quotas:</b> " + labels.get(4).getText() + "<br>"
                + "<b>Monthly livehouse Profit:</b> " + labels.get(5).getText()
                + "</p>"
                + "</body>"
                + "</html>";

        JEditorPane editorPane = new JEditorPane("text/html", content);
        editorPane.setEditable(false);
        editorPane.setPreferredSize(new Dimension(600, 200));
        editorPane.setBackground(Color.white);

        panel.add(editorPane);
        add(panel);

        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(1, 2));

        XChartPanel<PieChart> chartPanel1 = new XChartPanel<>(createPieChart());
        chartPanel.add(chartPanel1);

        XChartPanel<PieChart> chartPanel2 = new XChartPanel<>(createPieChart2());
        chartPanel.add(chartPanel2);

        this.add(chartPanel);
    }

    private PieChart createPieChart() {

        PieChart chart = new PieChartBuilder().width(400).height(400).build();
        Map<String, Double> data = new HashMap<>();
        data.put("Monthly performer profit", Double.parseDouble(labels.get(3).getText()));
        data.put("Monthly livehouse profit", Double.parseDouble(labels.get(5).getText()));

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            chart.addSeries(entry.getKey(), entry.getValue());
        }

        return chart;
    }

    private PieChart createPieChart2() {

        PieChart chart = new PieChartBuilder().width(400).height(400).build();
        Map<String, Double> data = new HashMap<>();

        double monthlyperfsales = Double.parseDouble(labels.get(2).getText());
        double unmet =  Double.parseDouble(labels.get(4).getText());
        double perfprofit = Double.parseDouble(labels.get(3).getText());
        double liveprofit = Double.parseDouble(labels.get(5).getText());
        
        data.put("Monthly performer sales", monthlyperfsales);
        data.put("Monthly unmet sales quotas",  unmet - perfprofit - liveprofit < 0 ? 0 : unmet - perfprofit - liveprofit);
       
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            chart.addSeries(entry.getKey(), entry.getValue());
        }

        return chart;
    }

    @Override
    public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {
        /* Fetch data from Controller using data from previous dialog window */
        List<Object[]> data = source.apply("sql/performer_report_month", sqlData[0]);

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

    @Override
    public boolean allowEmptyDatasets() {
        return false;
    }

    @Override
    public String getLoadFailureMessage() {
        return "There are no recorded performances by this performer in the database";
    }
}
