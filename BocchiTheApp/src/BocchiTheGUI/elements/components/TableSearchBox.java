package BocchiTheGUI.elements.components;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.function.Function;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TableSearchBox extends JPanel {
    private JTextField searchField;
    private int filterColumnIndex = 1;

    public Function<Object[], Boolean> getFilter() {
        return (row) -> {
            String rowString = (String) row[filterColumnIndex];
            String searchString = this.searchField.getText();

            return searchString == null || rowString.toLowerCase().contains(
                    searchString.toLowerCase());
        };
    }

    public TableSearchBox(String label, int filterColumnIndex, Runnable onUpdate) {
        this.setLayout(new GridLayout(2, 1));
        this.setBorder(new EmptyBorder(5, 20, 5, 20));

        this.searchField = new JTextField();
        this.filterColumnIndex = filterColumnIndex;

        this.searchField.setPreferredSize(new Dimension(300, 30));

        this.add(new JLabel(label));
        this.add(searchField);

        /* Add listener to search box */
        this.searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                onUpdate.run();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                onUpdate.run();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                onUpdate.run();
            }
        });
    }
}
