package BocchiTheGUI.elements.abstracts;

import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public abstract class TextFieldsUI extends PaneUI {
    protected List<JTextField> formItems;

    public TextFieldsUI(String dialogTitle) {
        super(dialogTitle);
    }

    /**
     * Adds {@link JTextField} forms to the UI
     * 
     * @param formItemLabels The labels to add to each form
     */
    protected void addForms(String... formItemLabels) {
        this.formItems = new ArrayList<>();

        this.setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));

        for (String formItemLabel : formItemLabels) {
            JPanel formItemPanel = new JPanel(new GridLayout());

            JTextField formItemTextField = new JTextField();
            formItemTextField.setColumns(15);
            this.formItems.add(formItemTextField);

            formItemPanel.add(new JLabel(formItemLabel));
            formItemPanel.add(formItemTextField);

            formItemPanel.setBorder(new EmptyBorder(5, 20, 5, 20));

            this.add(formItemPanel);
        }
    }

    /**
     * {@inheritDoc} Adds the same listener to each text field and automatically
     * sets their action commands to match that of the first button within the UI.
     */
    @Override
    public void addButtonListener(ActionListener listener) {
        super.addButtonListener(listener);

        this.formItems.forEach((formItem) -> {
            formItem.addActionListener(listener);
            formItem.setActionCommand(this.getDefaultButtonCommand());
        });
    }
}