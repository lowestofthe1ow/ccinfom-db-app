package BocchiTheGUI.components.abs;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public abstract class TableSelectionUI extends DialogUI {
    protected JTable table;
    private DefaultTableModel activeTableModel;
    private List<Object[]> tableRows;

    private JTextField searchField;
    private int searchColumnIndex;

    /**
     * Creates a dialog window for table selection.
     * 
     * @param name              Dialog window name
     * @param searchColumnIndex The index of the column used by the search box
     * @param columnNames       The names for each column
     */
    public TableSelectionUI(String name, int searchColumnIndex, String... columnNames) {
        super(name);
        this.setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));

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

        /* Wrap JTable in JScrollPane and add it */
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(500, 250));
        this.add(scrollPane);

        /* Initialize search box */
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 1));
        searchPanel.setBorder(new EmptyBorder(5, 20, 5, 20));

        this.searchField = new JTextField();
        this.searchField.setPreferredSize(new Dimension(300, 30));
        this.searchColumnIndex = searchColumnIndex;

        searchPanel.add(new JLabel("Search by " + columnNames[searchColumnIndex] + ": "));
        searchPanel.add(searchField);
        this.add(searchPanel);

        /* Add listener to search box */
        this.searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }
        });
    }

    /**
     * Updates the table with data.
     * 
     * @param data The list of row data to insert into the table
     */
    public void loadTableData(List<Object[]> data) {
        /* Reset the table model */
        activeTableModel.setRowCount(0);
        table.clearSelection();
        tableRows.clear();

        for (Object[] row : data) {
            activeTableModel.addRow(row);
            tableRows.add(row);
        }
    }

    private void filterTable() {
        String searchText = searchField.getText().toLowerCase();

        /* Reset the table model */
        activeTableModel.setRowCount(0);

        for (Object[] row : tableRows) {
            /* TODO: Change search column based on mouse click */
            String rowString = (String) row[searchColumnIndex];
            if (rowString.toLowerCase().contains(searchText)) {
                activeTableModel.addRow(row);
            }
        }

        table.clearSelection();
    }

    /**
     * {@inheritDoc} Each query represents a row included in the table selection
     * (which may include multiple rows).
     */
    @Override
    public Object[][] getSQLParameterInputs() {
        /* Get selected rows and create an empty ArrayList */
        int[] selectedRowIndices = table.getSelectedRows();
        ArrayList<Object[]> retval = new ArrayList<>();

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
