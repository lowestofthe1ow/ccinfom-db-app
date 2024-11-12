package BocchiTheGUI;

import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public abstract class TableSelectionUI extends DialogUI {
    private JTextField searchField;
    protected JTable table;
    private DefaultTableModel tableModel;
    private List<Object[]> TABLEPERM;

	public TableSelectionUI(String name, String... columnNames) {
    	super(name);
        setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        add(searchField);
        
        TABLEPERM = new ArrayList<>();
        
        Object[][] names = null;
        
        tableModel = new DefaultTableModel(names, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        table = new JTable(tableModel);
        
        
        
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        
        
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
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

    public void updateTable(List<Object[]> data) {
        tableModel.setRowCount(0);
       
        for (Object[] row : data) {
            tableModel.addRow(row);
            TABLEPERM.add(row);
        }
    }

    
    /*
     * 
     * 	TODO: Make a mouse listener that listens to what column was clicked and 
     * switch the search priority for that column
     * 
     * or a Button besides JTextfield that can be repeatedly pressed to switch search priority
     * 
     */
    
    private void filterTable() {
        String searchText = searchField.getText().toLowerCase();

        tableModel.setRowCount(0);
        for (Object[] row : TABLEPERM) {
            String rowString = (String) row[1]; 
            if (rowString.toLowerCase().contains(searchText)) {
                tableModel.addRow(row);
            }
        }
    }
    
	
}
