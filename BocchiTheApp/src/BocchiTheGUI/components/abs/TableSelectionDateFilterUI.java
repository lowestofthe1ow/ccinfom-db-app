package BocchiTheGUI.components.abs;

import java.awt.GridLayout;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import raven.datetime.component.date.DateEvent;
import raven.datetime.component.date.DatePicker;
import raven.datetime.component.date.DateSelectionListener;

public class TableSelectionDateFilterUI extends TableSelectionUI {
    private int searchColumnIndex;
    private DatePicker datePicker;

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

        datePicker = new DatePicker();
        JFormattedTextField dateEditor = new JFormattedTextField();
        // dateEditor.setPreferredSize(new Dimension(25, 25));
        datePicker.setEditor(dateEditor);
        this.searchColumnIndex = searchColumnIndex;

        datePicker.addDateSelectionListener(new DateSelectionListener() {
            @Override
            public void dateSelected(DateEvent event) {
                filterTable();
            }
        });

        // searchPanel.add(new JLabel("Search by " + columnNames[searchColumnIndex] + ":
        // "));
        searchPanel.add(new JLabel("Filter by start date: "));
        searchPanel.add(dateEditor);
        this.add(searchPanel);
    }

    private void filterTable() {
        LocalDate date = datePicker.getSelectedDate();

        if (date == null)
            return;

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

    /*
     * Override the loadTableData() function so that the table is immediately
     * filtered
     */
    @Override
    public void loadTableData(List<Object[]> data) {
        super.loadTableData(data);
        filterTable();
    }
}
