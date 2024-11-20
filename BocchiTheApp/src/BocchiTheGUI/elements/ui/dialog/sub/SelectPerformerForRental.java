package BocchiTheGUI.elements.ui.dialog.sub;

import java.awt.Dimension;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;
import BocchiTheGUI.elements.components.LabelForm;
import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.date.DateSelectionListener;

public class SelectPerformerForRental extends TableSelectionUI {
    private Object[][] sqlData;
    private LocalDate startDate;
    private LocalDate endDate;

    public SelectPerformerForRental(Object[][] sqlData) {
        super("Select performer", "ID", "Performer name", "Contact person", "Contact no.");
        this.setLoadDataCommand("sql/get_performers");
        this.addSearchBoxFilter("Filter by performer name", 1);

        JPanel startDatePanel = createDatePickerPanel("Start");
        JPanel endDatePanel = createDatePickerPanel("End");
        this.add(startDatePanel);
        this.add(endDatePanel);

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/sql/rent_equipment");
        this.addTerminatingCommands("button/sql/rent_equipment");
        this.setRoot("dialog/rent_equipment");
        this.sqlData = sqlData;
    }

    private JPanel createDatePickerPanel(String labelText) {
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
        
        return new LabelForm(labelText + " Date:", dateEditor);
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        List<Object> params = new ArrayList<>();

        
        params.add(super.getSQLParameterInputs()[0][0]);
        Collections.addAll(params, sqlData[0]);

        params.add(startDate.toString());
        params.add(endDate.toString());

        Object[][] retval = {
                params.toArray()
        };

        for (int i = 0; i < 4; i++)
            System.out.println(params.get(i));

        return retval;
    }
}
