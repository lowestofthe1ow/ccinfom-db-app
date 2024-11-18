package BocchiTheGUI.components.ui;

import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JLabel;

import BocchiTheGUI.components.abs.TableSelectionUI;
import BocchiTheGUI.interfaces.DataLoadable;

public class MonthlyLivehouseRevenueTab extends TableSelectionUI {
    private Object[][] sqlData;

    public MonthlyLivehouseRevenueTab(Object[][] sqlData) {
        super("Monthly livehouse revenue", "ID", "Performer name", "Start timestamp", "Status");
        this.sqlData = sqlData;
        this.setLoadDataCommand("sql/get_performances_in_month");
        this.setLoadDataParams((String[]) sqlData[0]);

        this.addSearchBoxFilter("Filter by performer name", 1);

        this.addButtons("Record revenue", "Cancel performance");
        this.setButtonActionCommands("button/next/record_revenue", "button/sql/cancel_performance");
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        return null;
    }

    @Override
    public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {
        super.loadData(source);
        List<Object[]> data = source.apply("sql/cut_report_month", sqlData[0]);

        this.add(new JLabel(data.get(0)[0].toString()));
    }
}
