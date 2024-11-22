package BocchiTheGUI.elements.ui.tab;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import org.knowm.xchart.style.PieStyler;
import org.knowm.xchart.style.PieStyler.LabelType;
import org.knowm.xchart.style.theme.XChartTheme;
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

        /* pI CHART 1 */
        Map<String, Double> chartData1 = new HashMap<>();
        chartData1.put("Monthly performer profit", Double.parseDouble(labels.get(3).getText()));
        chartData1.put("Monthly livehouse profit", Double.parseDouble(labels.get(5).getText()));

        XChartPanel<PieChart> chartPanel1 = new XChartPanel<>(createStyledPieChart(chartData1));
        chartPanel1.setOpaque(false);
        chartPanel.add(chartPanel1);

        /* pI CHART 2 */
        Map<String, Double> chartData2 = new HashMap<>();
        double monthlyperfsales = Double.parseDouble(labels.get(2).getText());
        double unmet = Double.parseDouble(labels.get(4).getText());
        double perfprofit = Double.parseDouble(labels.get(3).getText());
        double liveprofit = Double.parseDouble(labels.get(5).getText());

        chartData2.put("Monthly performer sales", monthlyperfsales);
        chartData2.put("Monthly unmet sales quotas", (unmet - perfprofit - liveprofit < 0 ? 0 : unmet - perfprofit - liveprofit));

        XChartPanel<PieChart> chartPanel2 = new XChartPanel<>(createStyledPieChart(chartData2));
        chartPanel2.setOpaque(false);
        chartPanel.add(chartPanel2);


        this.add(chartPanel);
    }
    private PieChart createStyledPieChart(Map<String, Double> data) {
     
        PieChart chart = new PieChartBuilder().width(400).height(400).build();
        
       
        for (Map.Entry<String, Double> entry : data.entrySet()) {
            chart.addSeries(entry.getKey(), entry.getValue());
        }

        Color[] sliceColors = new Color[] {new Color(250, 181, 200), new Color(210, 95, 90) };
        chart.getStyler().setSeriesColors(sliceColors);
        chart.getStyler().setChartBackgroundColor(getBackground());
        chart.getStyler().setLegendVisible(true);
        chart.getStyler().setLegendPosition(PieStyler.LegendPosition.InsideSE);
        chart.getStyler().setForceAllLabelsVisible(true);
        chart.getStyler().setLabelsFontColorAutomaticEnabled(true);
        chart.getStyler().setLabelsFont(new Font("SansSerif", Font.PLAIN, 15)); 
        chart.getStyler().setLabelType(LabelType.Percentage);
        chart.getStyler().setPlotContentSize(.65);
        chart.getStyler().setStartAngleInDegrees(90);

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
