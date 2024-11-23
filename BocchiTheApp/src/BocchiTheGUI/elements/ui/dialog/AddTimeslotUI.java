package BocchiTheGUI.elements.ui.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import BocchiTheGUI.elements.abstracts.PaneUI;
import BocchiTheGUI.elements.components.LabelForm;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.date.DateSelectionListener;
import raven.datetime.component.time.TimeEvent;
import raven.datetime.component.time.TimePicker;
import raven.datetime.component.time.TimeSelectionListener;

public class AddTimeslotUI extends PaneUI {

    /* would love to make this fields less but.... idk how */
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;

    public AddTimeslotUI() {
        super("Add Timeslot");

        JPanel startPanel = createDateTimePickerPanel("Start");
        JPanel endPanel = createDateTimePickerPanel("End");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));

        panel.add(startPanel);
        panel.add(endPanel);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.add(panel);

        addButtons("Confirm");
        setButtonActionCommands("button/sql/add_performance_timeslot");
        addTerminatingCommands("button/sql/add_performance_timeslot");
    }

    private JPanel createDateTimePickerPanel(String labelText) {
        DatePicker datePicker = new DatePicker();
        JFormattedTextField dateEditor = new JFormattedTextField();
        dateEditor.setPreferredSize(new Dimension(100, 25));
        datePicker.setEditor(dateEditor);

        datePicker.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent event) {
                if (labelText.equals("Start")) {
                    startDate = datePicker.getSelectedDate();
                } else {
                    endDate = datePicker.getSelectedDate();
                }
            }
        });

        // Create the TimePicker
        TimePicker timePicker = new TimePicker();
        JFormattedTextField timeEditor = new JFormattedTextField();
        timeEditor.setPreferredSize(new Dimension(100, 25));
        timePicker.setEditor(timeEditor);

        // Set up the TimeSelectionListener
        timePicker.addTimeSelectionListener(new TimeSelectionListener() {
            @Override
            public void timeSelected(TimeEvent event) {
                if (labelText.equals("Start")) {
                    startTime = timePicker.getSelectedTime();
                } else {
                    endTime = timePicker.getSelectedTime();
                }
            }
        });

        JPanel dateTimeEditor = new JPanel();
        dateTimeEditor.add(dateEditor);
        dateTimeEditor.add(timeEditor);

        return new LabelForm(labelText + " Date/Time:", dateTimeEditor);
    }

    @Override
    public Object[][] getSQLParameterInputs() {
 
    	if(startDate == null || startTime == null || endDate == null || endTime == null) {
    		 JOptionPane.showMessageDialog(null, "Inputs cannot be null");
    		 return new Object[0][0]; 
    	}
    	
    	
    	
            return new Object[][] {
                    { startDate, startTime, endDate, endTime }
            };
      
    }
}
