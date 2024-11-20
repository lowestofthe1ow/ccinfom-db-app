package BocchiTheGUI.elements.ui.dialog;

import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JComboBox;

import BocchiTheGUI.elements.abstracts.PaneUI;
import BocchiTheGUI.elements.components.LabelForm;
import BocchiTheGUI.interfaces.DataLoadable;

public class StaffSalaryUI extends PaneUI implements DataLoadable {
    private JComboBox<String> monthSelector;

    public StaffSalaryUI() {
        super("Generate monthly staff salary report");

        this.monthSelector = new JComboBox<>();
        this.add(new LabelForm("Select month", monthSelector));

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/report/staff_salary");
        this.addTerminatingCommands("button/report/staff_salary");
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

    @Override
    public boolean allowEmptyDatasets() {
        return false;
    }

    @Override
    public String getLoadFailureMessage() {
        return "There are no recorded performances in the database";
    }
}
