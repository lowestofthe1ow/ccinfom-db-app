package BocchiTheGUI.elements.components;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class LabelForm extends JPanel {
    public void setContent(JLabel label, Component form) {
        this.removeAll();
        
        this.add(label);
        this.add(form);
    }

    public void setContent(String label, Component form) {
        this.removeAll();
        
        this.add(new JLabel(label));
        this.add(form);
    }

    public LabelForm(JLabel label, Component form) {
        this();
        this.setContent(label, form);
    }

    public LabelForm(String label, Component form) {
        this();
        this.setContent(label, form);
    }

    public LabelForm() {
        this.setLayout(new GridLayout(1, 2));
        this.setBorder(new EmptyBorder(5, 20, 5, 20));
    }
}
