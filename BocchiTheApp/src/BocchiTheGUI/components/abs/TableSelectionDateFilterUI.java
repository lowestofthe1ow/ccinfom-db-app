package BocchiTheGUI.components.abs;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
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
        // searchPanel.setLayout(new GridLayout(1, 2));
        searchPanel.setBorder(new EmptyBorder(5, 20, 5, 20));

        DatePicker datePicker = new DatePicker();
        JFormattedTextField dateEditor = new JFormattedTextField();
        // dateEditor.setPreferredSize(new Dimension(25, 25));
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
        JPanel headerPanel = new JPanel();
        headerPanel.add(new JLabel("Filter by date:"));
        headerPanel.setBorder(new EmptyBorder(5, 20, 5, 20));
        this.add(headerPanel);
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
                activeTableModel.addRow(row);
            }
        }

        table.clearSelection();
    }
}
