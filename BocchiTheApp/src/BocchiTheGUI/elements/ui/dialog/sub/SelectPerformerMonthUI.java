package BocchiTheGUI.elements.ui.dialog.sub;

import java.util.List;
import java.util.function.BiFunction;

import BocchiTheGUI.elements.ui.dialog.MonthlyLivehouseRevenueUI;

public class SelectPerformerMonthUI extends MonthlyLivehouseRevenueUI {
    private Object[][] sqlData;

    public SelectPerformerMonthUI(Object[][] sqlData) {
        this.sqlData = sqlData;
        this.setButtonActionCommands("button/report/performer_report_month");
        this.addTerminatingCommands("button/report/performer_report_month");
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = {
                {
                        sqlData[0][0],
                        super.getSQLParameterInputs()[0][0],
                        super.getSQLParameterInputs()[0][1]
                }
        };
        return retval;
    }

    @Override
    public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {
        super.loadData((command, params) -> {
            return source.apply("sql/get_months_with_performances_by", sqlData[0]);
        });
    }
}
