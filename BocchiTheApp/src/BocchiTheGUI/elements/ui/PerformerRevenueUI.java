package BocchiTheGUI.elements.ui;

import java.awt.GridLayout;
import java.sql.Timestamp;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BocchiTheGUI.elements.abs.TableSelectionUI;
import raven.datetime.component.date.DatePicker;

public class PerformerRevenueUI extends TableSelectionUI {
    private DatePicker datePicker;

    public PerformerRevenueUI() {
        super("Generate performer revenue summary", "ID", "Performer", "Contact person", "Contact number");

        this.setLoadDataCommand("sql/get_performers");

        this.addSearchBoxFilter("Filter by performer name", 1);
        this.addSearchBoxFilter("Filter by contact person name", 2);

        // TODO: Should this be in a new dialog?
        datePicker = new DatePicker();
        JFormattedTextField dateEditor = new JFormattedTextField();
        datePicker.setEditor(dateEditor);

        JPanel dateEditorPanel = new JPanel();
        dateEditorPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
        dateEditorPanel.setLayout(new GridLayout(2, 1));

        dateEditorPanel.add(new JLabel("Select a date"));
        dateEditorPanel.add(dateEditor);
        this.add(dateEditorPanel);

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/report/performer_report_day");
        this.addTerminatingCommands("button/report/performer_report_day");
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = {
                {
                        super.getSQLParameterInputs()[0][0],
                        Timestamp.valueOf(datePicker.getSelectedDate().atStartOfDay())
                }
        };
        return retval;
    }
}
