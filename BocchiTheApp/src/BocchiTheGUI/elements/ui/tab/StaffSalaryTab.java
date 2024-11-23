package BocchiTheGUI.elements.ui.tab;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;
import BocchiTheGUI.elements.components.LabelForm;

public class StaffSalaryTab extends TableSelectionUI {
    public StaffSalaryTab(Object[][] sqlData) {
        super("Monthly staff salary", "ID", "Staff name", "Contact no.", "Total monthly salary");
        this.setLoadDataCommand("sql/get_staff_salary");
        this.setLoadDataParams((String[]) sqlData[0]);

        JLabel label = new JLabel("Monthly salaries for " + (String) sqlData[0][0] + " " + (String) sqlData[0][1]);
        label.setFont(new Font("IBM Plex Sans", Font.BOLD, 18));

        this.add(new LabelForm(label, new JPanel()));
        this.addSearchBoxFilter("Filter by staff name", 1);
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        return null;
    }

    @Override
    public String getLoadFailureMessage() {
        return "No salary data found";
    }
}
