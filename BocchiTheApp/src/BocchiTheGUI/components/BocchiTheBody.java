package BocchiTheGUI.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BocchiTheBody extends JPanel {

    private JTabbedPane tabbedPane;
    private JPanel centerPanel;
    private BocchiTheSidePanel sidePanel;

    public BocchiTheBody(String... strings) {
        this.setLayout(new BorderLayout());

        tabbedPane = new JTabbedPane();
        centerPanel = new JPanel(new BorderLayout());
        sidePanel = new BocchiTheSidePanel();

        this.add(new BocchiTheTabbedPane());

        showInstructionsPanel();
    }

    private JMenu createHomeMenu() {
        JMenu menu = new JMenu("Home");

        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInstructionsPanel();
            }
        });
        return menu;
    }

    private void showInstructionsPanel() {
        JPanel instructionsPanel = new JPanel();
        JLabel instructionsLabel = new JLabel("<html><body><h3>Welcome to Bocchi the DB Application!</h3>" +
                "<ul>" +
                "<li>Click the \"+\" to view tables.</li>" +
                "<li>Note: the option to view tables are not implemented yet.</li>" +
                "<li>Kita? IKUYO!.</li>" +
                "</ul>" +
                "</body></html>");
        instructionsPanel.add(instructionsLabel);

        centerPanel.removeAll();
        centerPanel.add(instructionsPanel, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

}
