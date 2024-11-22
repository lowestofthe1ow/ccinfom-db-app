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

import BocchiTheGUI.elements.abstracts.TableSelectionUI;
import BocchiTheGUI.elements.components.LabelForm;

public class PerformerRevenueTab extends TableSelectionUI {
    private Object[][] sqlData;
    private ArrayList<JLabel> labels;

    public PerformerRevenueTab(Object[][] sqlData) {
        super("Performer Report Day", "Performer name", "Timestamp", "Quota", "Ticket sales");
        this.sqlData = sqlData;
        this.labels = new ArrayList<>();
    }

    private void displayItPretty() {
        this.add(new LabelForm("Total monthly sales: ", new JLabel("PHP " + labels.get(2).getText())));
        this.add(new LabelForm("Total monthly performer profit: ", new JLabel("PHP " + labels.get(3).getText())));
        this.add(new LabelForm("Total monthly unmet sales quotas: ", new JLabel("PHP " + labels.get(4).getText())));
        this.add(new LabelForm("Total monthly livehouse profit: ", new JLabel("PHP " + labels.get(5).getText())));
       // this.add(panel);

        JPanel chartPanel = new JPanel();
        chartPanel.setLayout(new GridLayout(1, 2));

        XChartPanel<PieChart> chartPanel1 = new XChartPanel<>(createPieChart());
        chartPanel1.setOpaque(false);
        chartPanel.add(chartPanel1);

        XChartPanel<PieChart> chartPanel2 = new XChartPanel<>(createPieChart2());
        chartPanel1.setOpaque(false);
        chartPanel.add(chartPanel2);

        this.add(chartPanel);
    }

    // jesus christ why are there two again
    private PieChart createPieChart() {

        PieChart chart = new PieChartBuilder().width(400).height(400).build();
        Map<String, Double> data = new HashMap<>();
        data.put("Monthly performer profit", Double.parseDouble(labels.get(3).getText()));
        data.put("Monthly livehouse profit", Double.parseDouble(labels.get(5).getText()));

        for (Map.Entry<String, Double> entry : data.entrySet()) {
            chart.addSeries(entry.getKey(), entry.getValue());
        }

        chart.getStyler().setChartBackgroundColor(new Color(0, 0, 0, 0));

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

        chart.getStyler().setChartBackgroundColor(new Color(0, 0, 0, 0));

        return chart;
    }

    @Override
    public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {
        super.loadData((command, params) -> {
            return source.apply("sql/get_performances_by_performer_month", sqlData[0]);
        });

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
