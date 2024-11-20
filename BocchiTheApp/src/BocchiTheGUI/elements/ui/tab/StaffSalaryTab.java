package BocchiTheGUI.elements.ui.tab;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class StaffSalaryTab extends TableSelectionUI {
    public StaffSalaryTab(Object[][] sqlData) {
        super("Monthly staff salary", "ID", "Staff name", "Contact no.", "Total monthly salary");
        this.setLoadDataCommand("sql/get_staff_assignments");
        this.setLoadDataParams((String[]) sqlData[0]);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 2));
        labelPanel.setBorder(new EmptyBorder(5, 20, 5, 20));

        JLabel label = new JLabel("Monthly salaries for " + (String) sqlData[0][0] + " " + (String) sqlData[0][1]);
        label.setFont(new Font("IBM Plex Sans", Font.BOLD, 18));
        labelPanel.add(label);

        this.addSearchBoxFilter("Filter by staff name", 1);
        this.add(labelPanel);
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        return null;
    }
}
