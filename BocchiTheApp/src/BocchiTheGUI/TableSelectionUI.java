package BocchiTheGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

public class TableSelectionUI extends JPanel {
    private JTextField searchField;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<Object[]> TABLEPERM;

    @SuppressWarnings("serial")
	public TableSelectionUI(String[] columnNames) {
        setLayout(new BorderLayout());

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        add(searchField, BorderLayout.NORTH);

        TABLEPERM = new ArrayList<>();
        
        Object[][] names = null;
        
        tableModel = new DefaultTableModel(names, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

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

    public void updateTable(Object[][] data) {
        tableModel.setRowCount(0);
       
        for (Object[] row : data) {
            tableModel.addRow(row);
            TABLEPERM.add(row);
        }
    }

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
    
    public Integer getSelectedID() {
        int selectedRowIndex = table.getSelectedRow();
     
        if (selectedRowIndex == -1) {
            return null;  
        }
        
        Object idObject = table.getValueAt(selectedRowIndex, 0); 
        if (idObject instanceof Integer) {
            return (Integer) idObject;  
        } else {
          
            throw new IllegalArgumentException("Selected row does not have an Integer ID.");
        }
    } 
}
