package BocchiTheGUI;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class BocchiTheBody extends JPanel{
	
	BocchiTheBody(){
		this.setLayout(new BorderLayout());
		JTabbedPane houses = new JTabbedPane();
		
		//houses.addTab("Tab 1", null, makeTextPanel("Panel #1"), null);
		//houses.addTab("Tab 2", null, makeTextPanel("Panel #2"), null);
		//houses.addTab("Tab 3", null, makeTextPanel("Panel #3"), null);
		//houses.addTab("Tab 4", null, makeTextPanel("Panel #4"), null);
		///houses.addTab("Tab 5", null, null, null);
		//houses.addTab("Tab 6", null, null, null);
		
		this.add(houses);
	}
	
	public JPanel makeTextPanel(String text) {
        JPanel panel = new JPanel(); 
        JLabel label = new JLabel(text); 
        panel.add(label); 
        return panel; 
    }
}
