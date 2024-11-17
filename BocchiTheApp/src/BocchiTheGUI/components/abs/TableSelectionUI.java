package BocchiTheGUI.components.abs;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import BocchiTheGUI.components.TableComboBox;
import BocchiTheGUI.components.TableDatePicker;
import BocchiTheGUI.components.TableSearchBox;
import BocchiTheGUI.interfaces.DataLoadable;

public abstract class TableSelectionUI extends DialogUI implements DataLoadable {
    private JTable table;
    private DefaultTableModel activeTableModel;
    private List<Object[]> tableRows;
    private String sqlLoadDataCommand;

    /**
     * A list of {@link Function}s that are called by the table during
     * {@link #filterTable()}.
     */
    private List<Function<Object[], Boolean>> filters;

    public void setLoadDataCommand(String command) {
        this.sqlLoadDataCommand = command;
    }

    /**
     * Creates a dialog window for table selection.
     * 
     * @param name              Dialog window name
     * @param searchColumnIndex The index of the column used by the search box
     * @param columnNames       The names for each column
     */
    public TableSelectionUI(String name, String... columnNames) {
        super(name);
        this.setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));

        this.filters = new ArrayList<>();

        /* Create an empty list of row data */
        this.tableRows = new ArrayList<>();

        /* Create an empty table model */
        this.activeTableModel = new DefaultTableModel(null, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        /* Create JTable based on model */
        this.table = new JTable(activeTableModel);
        this.table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        this.table.getTableHeader().setReorderingAllowed(false);
        this.table.setAutoCreateRowSorter(true);

        /* Wrap JTable in JScrollPane and add it */
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 250));
        this.add(scrollPane);
    }

    /**
     * Adds a {@link Function} to the list of filters used by the table
     * 
     * @param filter The filter to add, usually provided by the component
     */
    private void addFilter(Function<Object[], Boolean> filter) {
        this.filters.add(filter);
    }

    /**
     * Adds a search box filter to the UI.
     * 
     * @param label             The label to add to the search box
     * @param filterColumnIndex The index of the table column to filter by
     */
    protected void addSearchBoxFilter(String label, int filterColumnIndex) {
        TableSearchBox searchBox = new TableSearchBox(label, filterColumnIndex, () -> {
            this.filterTable();
        });
        this.addFilter(searchBox.getFilter());
        this.add(searchBox);
    }

    /**
     * Adds a date picker filter to the UI.
     * 
     * @param label             The label to add to the date picker
     * @param filterColumnIndex The index of the table column to filter by
     */
    protected void addDatePickerFilter(String label, int filterColumnIndex) {
        TableDatePicker datePicker = new TableDatePicker(label, filterColumnIndex, () -> {
            this.filterTable();
        });
        this.addFilter(datePicker.getFilter());
        this.add(datePicker);
    }

    /**
     * Adds a combo box filter to the UI.
     * 
     * @param label             The label to add to the combo box
     * @param filterColumnIndex The index of the column to filter by
     * @param options           The options to add to the combo box
     */
    protected void addComboBoxFilter(String label, int filterColumnIndex, String... options) {
        TableComboBox comboBox = new TableComboBox(label, filterColumnIndex, () -> {
            this.filterTable();
        });
        comboBox.addOptions(options);
        this.addFilter(comboBox.getFilter());
        this.add(comboBox);
    }

    /**
     * Filters the table according to the list of filters in the table
     */
    private void filterTable() {
        /* Reset the table model */
        activeTableModel.setRowCount(0);

        for (Object[] row : tableRows) {
            /* The row must meet ALL filter conditions to be added */
            if (filters.stream().allMatch(filter -> filter.apply(row))) {
                this.activeTableModel.addRow(row);
            }
        }

        table.clearSelection();
    }

    /**
     * Updates the table with data.
     * 
     * @param data The list of row data to insert into the table
     */
    @Override
    public void loadData(Function<Object, List<Object[]>> source) {
        List<Object[]> data = source.apply(this.sqlLoadDataCommand);

        /* Reset the table model */
        activeTableModel.setRowCount(0);
        table.clearSelection();
        tableRows.clear();

        for (Object[] row : data) {
            activeTableModel.addRow(row);
            tableRows.add(row);
        }

        filterTable();
    }

    /**
     * {@inheritDoc} Each query represents a row included in the table selection
     * (which may include multiple rows).
     */
    @Override
    public Object[][] getSQLParameterInputs() {
        /* Get selected rows and create an empty list */
        int[] selectedRowIndices = table.getSelectedRows();
        List<Object[]> retval = new ArrayList<>();

        for (int selectedRowIndex : selectedRowIndices) {
            /* Create an array containing only the audition ID */
            /* TODO: Specify which columns to include in SQL */
            Object[] val = { table.getValueAt(selectedRowIndex, 0) };

            /* Verify that the ID is an integer */
            if (val[0] instanceof Integer) {
                retval.add(val);
            } else {
                throw new IllegalArgumentException("Selected row(s) does not have an Integer ID.");
            }
        }

        /*
         * Return an array of singleton array (each singleton is processed as the
         * parameter passed to the SQL stored procedure)
         */
        return retval.toArray(new Object[retval.size()][]);
    }
}
