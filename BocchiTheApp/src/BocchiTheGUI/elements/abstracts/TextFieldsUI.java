package BocchiTheGUI.elements.abstracts;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

import BocchiTheGUI.elements.components.LabelForm;

public abstract class TextFieldsUI extends PaneUI {
    protected List<JTextField> formItems;
    private JPanel northPanel;

    public TextFieldsUI(String dialogTitle) {
        super(dialogTitle);

        this.setLayout(new BorderLayout());
        this.northPanel = new JPanel();
        this.northPanel.setLayout((LayoutManager) new BoxLayout(this.northPanel, BoxLayout.Y_AXIS));
        super.add(this.northPanel, BorderLayout.NORTH);
    }

    /**
     * Adds {@link JTextField} forms to the UI
     * 
     * @param formItemLabels The labels to add to each form
     */
    protected void addForms(String... formItemLabels) {
        this.formItems = new ArrayList<>();

        for (String formItemLabel : formItemLabels) {
            JTextField formItemTextField = new JTextField();
            formItemTextField.setColumns(15);
            formItemTextField.setMaximumSize(formItemTextField.getPreferredSize());
            this.formItems.add(formItemTextField);

            this.add(new LabelForm(formItemLabel, formItemTextField));
        }
    }

    @Override
    public Component add(Component comp) {
        // if (!(comp instanceof JTable || comp instanceof JScrollPane))
        // comp.setPreferredSize(new Dimension(500, 20));
        this.northPanel.add(comp);
        this.northPanel.revalidate();
        this.northPanel.repaint();
        return comp;
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