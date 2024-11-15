package BocchiTheGUI.components.abs;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.date.DateSelectionListener;

public class TableSelectionDateFilterUI extends TableSelectionUI {
    private int searchColumnIndex;

    /**
     * Creates a dialog window for table selection.
     * 
     * @param name              Dialog window name
     * @param searchColumnIndex The index of the column used by the search box
     * @param columnNames       The names for each column
     */
    public TableSelectionDateFilterUI(String name, int searchColumnIndex, String... columnNames) {
        super(name, columnNames);

        /* Initialize search box */
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 1));
        searchPanel.setBorder(new EmptyBorder(5, 20, 5, 20));

        DatePicker datePicker = new DatePicker();
        JFormattedTextField dateEditor = new JFormattedTextField();
        dateEditor.setPreferredSize(new Dimension(100, 25));
        datePicker.setEditor(dateEditor);
        this.searchColumnIndex = searchColumnIndex;

        datePicker.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent event) {
                filterTable(datePicker.getSelectedDate());
            }
        });

        // searchPanel.add(new JLabel("Search by " + columnNames[searchColumnIndex] + ":
        // "));
        searchPanel.add(datePicker);
        this.add(searchPanel);
    }

    private void filterTable(LocalDate date) {
        /* Reset the table model */
        activeTableModel.setRowCount(0);

        for (Object[] row : tableRows) {
            /* TODO: Change search column based on mouse click */
            Timestamp rowTimestamp = (Timestamp) row[searchColumnIndex];
            LocalDate rowDate = new Date(rowTimestamp.getTime()).toLocalDate();
            if (rowDate.compareTo(date) == 0) {
                System.out.println(row[1]);
                activeTableModel.addRow(row);
            }
        }

        table.clearSelection();
    }
}
