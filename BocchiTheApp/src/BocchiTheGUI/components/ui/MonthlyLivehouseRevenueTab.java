package BocchiTheGUI.components.ui;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BocchiTheGUI.components.abs.TableSelectionUI;

public class MonthlyLivehouseRevenueTab extends TableSelectionUI {
    private Object[][] sqlData;
    private JLabel revenueLabel;

    public MonthlyLivehouseRevenueTab(Object[][] sqlData) {
        super("Monthly livehouse revenue", "ID", "Performer name", "Start timestamp", "Status");
        this.sqlData = sqlData;
        this.setLoadDataCommand("sql/get_performances_in_month");
        this.setLoadDataParams((String[]) sqlData[0]);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 2));
        labelPanel.setBorder(new EmptyBorder(5, 20, 5, 20));

        JLabel label = new JLabel("Monthly revenue for " + (String) sqlData[0][0] + " " + (String) sqlData[0][1]);
        label.setFont(new Font("IBM Plex Sans", Font.BOLD, 18));
        labelPanel.add(label);

        revenueLabel = new JLabel();
        revenueLabel.setFont(new Font("IBM Plex Sans", Font.PLAIN, 18));
        labelPanel.add(revenueLabel);

        this.addSearchBoxFilter("Filter by performer name", 1);
        this.add(labelPanel);
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        return null;
    }

    @Override
    public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {
        super.loadData(source);
        List<Object[]> data = source.apply("sql/cut_report_month", sqlData[0]);

        this.revenueLabel.setText(String.format("PHP %.2f", data.get(0)[0]));
        this.revenueLabel.revalidate();
        this.revenueLabel.repaint();
    }
}
