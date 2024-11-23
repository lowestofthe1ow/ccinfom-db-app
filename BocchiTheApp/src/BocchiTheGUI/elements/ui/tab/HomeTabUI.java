package BocchiTheGUI.elements.ui.tab;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BocchiTheGUI.elements.abstracts.PaneUI;

public class HomeTabUI extends PaneUI {
    public HomeTabUI() {
        super("Generate Reports");

        this.setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon(getClass().getClassLoader().getResource("BocchiTheAssets/logo.png"));
        ImageIcon scaled = new ImageIcon(
                imageIcon.getImage().getScaledInstance(512, -1, java.awt.Image.SCALE_SMOOTH));

        JPanel panel = new JPanel();
        panel.add(new JLabel(scaled));
        panel.setBorder(new EmptyBorder(50, 50, 50, 50));
        panel.setBackground(new Color(255, 255, 255));

        this.add(panel, BorderLayout.NORTH);

        this.addButtons("Monthly performer sales",
                "Monthly livehouse sales",
                "Monthly rental sales",
                "Weekly livehouse schedule",
                "Staff salary report");

        this.setButtonActionCommands("dialog/performer_revenue",
                "dialog/monthly_livehouse_revenue",
                "dialog/monthly_rental_revenue",
                "report/livehouse_schedule",
                "dialog/staff_salary");

        this.buttonPanel.setBackground(new Color(255, 255, 255));
        this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        this.setBackground(Color.PINK);
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        return null;
    }
}