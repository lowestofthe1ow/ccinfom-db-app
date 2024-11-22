package BocchiTheGUI.elements.ui.tab;

import java.awt.Font;
import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JLabel;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;
import BocchiTheGUI.elements.components.LabelForm;

public class MonthlyLivehouseRevenueTab extends TableSelectionUI {
    private Object[][] sqlData;
    private JLabel revenueLabel;

    public MonthlyLivehouseRevenueTab(Object[][] sqlData) {
        super("Monthly livehouse revenue", "ID", "Performer name", "Start timestamp", "Livehouse profits");
        this.sqlData = sqlData;
        this.setLoadDataCommand("sql/get_performances_in_month");
        this.setLoadDataParams((String[]) sqlData[0]);

        JLabel label = new JLabel("Monthly revenue for " + (String) sqlData[0][0] + " " + (String) sqlData[0][1]);
        label.setFont(new Font("IBM Plex Sans", Font.BOLD, 18));

        revenueLabel = new JLabel();
        revenueLabel.setFont(new Font("IBM Plex Sans", Font.PLAIN, 18));

        this.add(new LabelForm(label, revenueLabel));
        this.addSearchBoxFilter("Filter by performer name", 1);
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
