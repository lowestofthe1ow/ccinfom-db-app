package BocchiTheGUI.elements.ui.tab;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class StaffSalaryTab extends TableSelectionUI {
    private Object[][] sqlData;
    private JLabel salaryLabel;

    public StaffSalaryTab(Object[][] sqlData) {
        super("Monthly staff salary report", "ID", "Staff name", "Assignments completed");
        this.sqlData = sqlData;
        this.setLoadDataCommand("sql/get_staff_assignments");
        this.setLoadDataParams((String[]) sqlData[0]);

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1, 2));
        labelPanel.setBorder(new EmptyBorder(5, 20, 5, 20));

        JLabel label = new JLabel("Salary in a month for " + (String) sqlData[0][0] + " " + (String) sqlData[0][1]);
        label.setFont(new Font("IBM Plex Sans", Font.BOLD, 18));
        labelPanel.add(label);

        salaryLabel = new JLabel();
        salaryLabel.setFont(new Font("IBM Plex Sans", Font.PLAIN, 18));
        labelPanel.add(salaryLabel);

        this.addSearchBoxFilter("Filter by staff name", 1);
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

        this.salaryLabel.setText(String.format("PHP %.2f", data.get(0)[0]));
        this.salaryLabel.revalidate();
        this.salaryLabel.repaint();
    }
}