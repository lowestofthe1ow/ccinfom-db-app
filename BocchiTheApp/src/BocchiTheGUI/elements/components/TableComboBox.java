package BocchiTheGUI.elements.components;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.function.Function;

import javax.swing.JComboBox;

public class TableComboBox extends LabelForm {
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
        this.filterColumnIndex = filterColumnIndex;
        this.comboBox = new JComboBox<String>();

        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                onUpdate.run();
            }
        });

        this.setContent(label, comboBox);
    }
}
