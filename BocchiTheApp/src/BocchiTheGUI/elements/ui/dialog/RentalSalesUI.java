package BocchiTheGUI.elements.ui.dialog;

import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JComboBox;

import BocchiTheGUI.elements.abstracts.PaneUI;
import BocchiTheGUI.elements.components.LabelForm;
import BocchiTheGUI.interfaces.DataLoadable;

public class RentalSalesUI extends PaneUI implements DataLoadable {
    private JComboBox<String> monthSelector;

    public RentalSalesUI() {
        super("Generate monthly rental sales report");

        this.monthSelector = new JComboBox<>();

        this.add(new LabelForm("Select month", monthSelector));

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/report/monthly_rental_sales");
        this.addTerminatingCommands("button/report/monthly_rental_sales");
    }

    @Override
    public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {
        List<Object[]> data = source.apply("sql/get_rental_months", null);

        data.forEach((row) -> {
            monthSelector.addItem((String) row[0] + " " + (Integer) row[1]);
        });

        /* Refresh the month selector */
        monthSelector.revalidate();
        monthSelector.repaint();
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = {
                ((String) this.monthSelector.getSelectedItem()).split(" ") };
        return retval;
    }

    @Override
    public boolean allowEmptyDatasets() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public String getLoadFailureMessage() {
        // TODO Auto-generated method stub
        return "There are currently no recorded sales";
    }

}
