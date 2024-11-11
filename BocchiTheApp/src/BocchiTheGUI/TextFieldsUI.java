package BocchiTheGUI;

import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public abstract class TextFieldsUI extends DialogUI {
    protected ArrayList<JTextField> formItems;

    protected TextFieldsUI(String... formItemLabels) {
        this.formItems = new ArrayList<>();

        this.setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));

        for (String formItemLabel : formItemLabels) {
            JPanel formItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            formItemPanel.add(new JLabel(formItemLabel));

            JTextField formItemTextField = new JTextField();
            formItemTextField.setColumns(15);
            this.formItems.add(formItemTextField);

            formItemPanel.add(formItemTextField);

            this.add(formItemPanel);
        }
    }

    public void removeText() {
        this.formItems.forEach((formItem) -> {
            formItem.setText("");
        });
    }
}