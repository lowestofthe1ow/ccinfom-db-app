package BocchiTheGUI.elements.ui.dialog;

import java.awt.GridLayout;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;
import raven.datetime.component.date.DatePicker;

public class PerformerRevenueUI extends TableSelectionUI {

    private JComboBox<String> monthSelector;
    
    public PerformerRevenueUI() {
        super("Generate performer revenue summary", "ID", "Performer", "Contact person", "Contact number");
        
        this.setLoadDataCommand("sql/get_performers");

        this.addSearchBoxFilter("Filter by performer name", 1);
        this.addSearchBoxFilter("Filter by contact person name", 2);

        JPanel monthSelectorPanel = new JPanel();
        monthSelectorPanel.setLayout(new GridLayout(1, 2));
        monthSelectorPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
        this.monthSelector = new JComboBox<>();

        monthSelectorPanel.add(new JLabel("Select month"));
        monthSelectorPanel.add(monthSelector);

        this.add(monthSelectorPanel);

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/report/performer_report_month");
        this.addTerminatingCommands("button/report/performer_report_month");
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = {
            {
                (Integer) super.getSQLParameterInputs()[0][0],
                ((String) this.monthSelector.getSelectedItem()).split(" ")[0],
                ((String) this.monthSelector.getSelectedItem()).split(" ")[1]  
            }
        };
        return retval;
    }
    
    @Override
    public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {
    	
    	super.loadData(source);
        List<Object[]> data = source.apply("sql/get_months_on_record", null);

        data.forEach((row) -> {
            monthSelector.addItem((String) row[0] + " " + (Integer) row[1]);
        });

        /* Refresh the month selector */
        monthSelector.revalidate();
        monthSelector.repaint();
    }
}
