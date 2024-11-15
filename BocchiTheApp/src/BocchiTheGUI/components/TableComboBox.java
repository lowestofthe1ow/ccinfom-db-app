package BocchiTheGUI.components;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.function.Function;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TableComboBox extends JPanel {
    private JComboBox<String> comboBox;
    private int filterColumnIndex;

    public Function<Object[], Boolean> getFilter() {
        return (row) -> {
            String rowString = (String) row[filterColumnIndex];
            String comboBoxString = (String) this.comboBox.getSelectedItem();

            return comboBoxString == null ||
                    rowString.toLowerCase().equals(comboBoxString.toLowerCase());
        };
    }

    public void addOptions(String... options) {
        for (String option : options) {
            this.comboBox.addItem(option);
        }
    }

    public TableComboBox(String label, int filterColumnIndex, Runnable onUpdate) {
        this.setLayout(new GridLayout(2, 1));
        this.setBorder(new EmptyBorder(5, 20, 5, 20));

        this.filterColumnIndex = filterColumnIndex;
        this.comboBox = new JComboBox<String>();

        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                onUpdate.run();
            }
        });

        this.add(new JLabel(label));
        this.add(comboBox);
    }
}
