package BocchiTheGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AuditionUI extends JPanel {
    private JTextField searchField;
    private JList<String> nameList;
    private DefaultListModel<String> listModel;
    private List<String> NAMELISTPERM;
    
    
    public AuditionUI() {
        setLayout(new BorderLayout());

        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(300, 30));
        add(searchField, BorderLayout.NORTH);
        NAMELISTPERM = new ArrayList<String>();
        listModel = new DefaultListModel<>();

       
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterList();
            }
        });
        
    
        
    }
    public void updateList(List<String> listPassed) {
    	NAMELISTPERM = listPassed;
    	for (String name : NAMELISTPERM) {
    		listModel.addElement(name);
        }
    	 
        nameList = new JList<>(listModel);
        nameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane listScroller = new JScrollPane(nameList);
        add(listScroller, BorderLayout.CENTER);
    }
    
    private void filterList() {
        String searchText = searchField.getText().toLowerCase();
        listModel.clear();

        
        for (String name : NAMELISTPERM) {
            if (name.toLowerCase().contains(searchText)) {
                listModel.addElement(name);
            }
        }
        
    }
}