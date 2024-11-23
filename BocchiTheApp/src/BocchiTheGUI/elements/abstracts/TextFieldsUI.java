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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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

    private void onTextUpdate() {
        boolean hasEmptyForm = formItems.stream().map(formItem -> formItem.getText()).anyMatch(string -> {
            return string == null || string.length() == 0;
        });
        
        if (hasEmptyForm)
            this.disableAllButtons();
        else
            this.enableAllButtons();
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

            formItemTextField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    onTextUpdate();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    onTextUpdate();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    onTextUpdate();
                }
            });

            this.formItems.add(formItemTextField);

            this.add(new LabelForm(formItemLabel, formItemTextField));
        }
    }

    @Override
    public Component add(Component comp) {
        this.northPanel.add(comp);
        this.northPanel.revalidate();
        this.northPanel.repaint();
        this.disableAllButtons();
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

    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = { this.formItems.stream().map(formItem -> formItem.getText()).toArray() };
        return retval;
    }
}