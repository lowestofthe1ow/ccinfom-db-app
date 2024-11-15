package BocchiTheGUI.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BocchiTheBody extends JPanel {

    private JMenuBar menuBar;
    private JPanel centerPanel;
    private BocchiTheSidePanel sidePanel;
    public BocchiTheBody(String... strings) {
        this.setLayout(new BorderLayout());
        
        centerPanel = new JPanel(new BorderLayout());
        sidePanel = new BocchiTheSidePanel();
        menuBar = new JMenuBar();
        
        // Add a home menu with instructions
        JMenu homeMenu = createHomeMenu();
        menuBar.add(homeMenu);

        for(String string :  strings) {
        	menuBar.add(new JMenu(string));
        }
        this.add(menuBar, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(sidePanel, BorderLayout.EAST);
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
