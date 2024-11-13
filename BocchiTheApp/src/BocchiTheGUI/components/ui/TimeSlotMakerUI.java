package BocchiTheGUI.components.ui;


import BocchiTheGUI.components.abs.DialogUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFormattedTextField;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.date.DateSelectionListener;
import raven.datetime.component.time.TimeEvent;
import raven.datetime.component.time.TimePicker;
import raven.datetime.component.time.TimeSelectionListener;


public class TimeSlotMakerUI extends DialogUI {
    
    /* would love to make this fields less but.... idk how */
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;

    public TimeSlotMakerUI() {
        super("Pick a date and time");
        
        JPanel startPanel = createDateTimePickerPanel("Start");
        JPanel endPanel = createDateTimePickerPanel("End");
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 3, 10));
        panel.add(startPanel);
        panel.add(endPanel);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(panel, BorderLayout.CENTER);

        addButtons("Confirm");
        setButtonActionCommands("add_performance_stimeslot");
    }

    private JPanel createDateTimePickerPanel(String labelText) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2, 3, 0));

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

        panel.add(new JLabel(labelText + " Date/Time:"));
        panel.add(dateEditor);
        panel.add(timeEditor);

        return panel;
    }

   
    @Override
    public Object[][] getSQLParameterInputs() {
        try {
            if (startDate == null || startTime == null || endDate == null || endTime == null) {
                System.out.println("Error: One or more date/time values are null.");
                return new Object[][] {};  // Return empty array if any value is missing
            }

            Timestamp startTimestamp = Timestamp.valueOf(LocalDateTime.of(startDate, startTime));
            Timestamp endTimestamp = Timestamp.valueOf(LocalDateTime.of(endDate, endTime));

            if (endTimestamp.before(startTimestamp)) {
                System.out.println("Error: End time must be after start time.");
                return new Object[][] {};  
            }

            System.out.println("Start Timestamp: " + startTimestamp);
            System.out.println("End Timestamp: " + endTimestamp);

            return new Object[][]{
                {startTimestamp, endTimestamp}
            };
        } catch (Exception e) {
            System.out.println("Error while creating SQL parameters: " + e.getMessage());
            e.printStackTrace();
            return new Object[][] {};  
        }
    }
}
