package BocchiTheGUI.components.abs;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TableSelectionSearchFilterUI extends TableSelectionUI {
    private JTextField searchField;
    private int searchColumnIndex;

    /**
     * Creates a dialog window for table selection.
     * 
     * @param name              Dialog window name
     * @param searchColumnIndex The index of the column used by the search box
     * @param columnNames       The names for each column
     */
    public TableSelectionSearchFilterUI(String name, int searchColumnIndex, String... columnNames) {
        super(name, columnNames);

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
}
