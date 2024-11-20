package BocchiTheGUI.elements.components;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.function.Function;

import javax.swing.JFormattedTextField;

import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.date.DateSelectionListener;

public class TableDatePicker extends LabelForm {
    private DatePicker datePicker;
    private int filterColumnIndex;

    public Function<Object[], Boolean> getFilter() {
        return (row) -> {
            LocalDate date = datePicker.getSelectedDate();
            Timestamp rowTimestamp = (Timestamp) row[filterColumnIndex];
            LocalDate rowDate = new Date(rowTimestamp.getTime()).toLocalDate();

            return date == null || rowDate.compareTo(date) == 0;
        };
    }

    public TableDatePicker(String label, int filterColumnIndex, Runnable onUpdate) {
        this.filterColumnIndex = filterColumnIndex;
        this.datePicker = new DatePicker();

        JFormattedTextField dateEditor = new JFormattedTextField();
        this.datePicker.setEditor(dateEditor);

        datePicker.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent event) {
                onUpdate.run();
            }
        });

        this.setContent(label, dateEditor);
    }
}
