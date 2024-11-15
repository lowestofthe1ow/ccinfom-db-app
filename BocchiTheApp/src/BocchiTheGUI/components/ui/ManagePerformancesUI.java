package BocchiTheGUI.components.ui;

import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BocchiTheGUI.components.abs.TableSelectionDateFilterUI;

public class ManagePerformancesUI extends TableSelectionDateFilterUI {
    public ManagePerformancesUI() {
        super("Manage performances", 2, "ID", "Performer", "Timeslot", "Status");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        panel.setBorder(new EmptyBorder(5, 20, 5, 20));
        panel.add(new JLabel("Only show performances with status: "));

        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.addItem("PENDING");
        panel.add(comboBox);

        this.add(panel);

        this.addButtons("Update");
        this.setButtonActionCommands("button/next/input_submission");
    }
}
