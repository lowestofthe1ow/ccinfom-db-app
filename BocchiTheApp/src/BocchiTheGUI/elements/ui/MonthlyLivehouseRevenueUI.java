package BocchiTheGUI.elements.ui;

import java.awt.GridLayout;
import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BocchiTheGUI.elements.abs.PaneUI;
import BocchiTheGUI.interfaces.DataLoadable;

public class MonthlyLivehouseRevenueUI extends PaneUI implements DataLoadable {
    private JComboBox<String> monthSelector;

    public MonthlyLivehouseRevenueUI() {
        super("Generate monthly livehouse revenue report");

        JPanel monthSelectorPanel = new JPanel();
        monthSelectorPanel.setLayout(new GridLayout(1, 2));
        monthSelectorPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
        this.monthSelector = new JComboBox<>();

        monthSelectorPanel.add(new JLabel("Select month"));
        monthSelectorPanel.add(monthSelector);

        this.add(monthSelectorPanel);

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/report/monthly_livehouse_revenue");
        this.addTerminatingCommands("button/report/monthly_livehouse_revenue");
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = { ((String) this.monthSelector.getSelectedItem()).split(" ") };
        return retval;
    }

    @Override
    public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {
        List<Object[]> data = source.apply("sql/get_months_on_record", null);

        data.forEach((row) -> {
            monthSelector.addItem((String) row[0] + " " + (Integer) row[1]);
        });

        /* Refresh the month selector */
        monthSelector.revalidate();
        monthSelector.repaint();
    }
}
