package BocchiTheGUI.elements.ui.tab;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import BocchiTheGUI.elements.abstracts.PaneUI;

public class HomeTabUI extends PaneUI {

	public HomeTabUI() {
		super("Generate Reports");

		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.LINE_AXIS));
		
		JLabel welcomeLabel = new JLabel("BOCCHI THE APP");
		welcomeLabel.setFont(new Font("Impact", Font.BOLD, 72)); 
		welcomeLabel.setForeground(Color.WHITE); 
		welcomeLabel.setAlignmentX(CENTER_ALIGNMENT); 
		
		
		main.add(welcomeLabel);
		main.setBackground(Color.PINK);
		
		
		this.add(main);
		

		this.addButtons("Monthly performer sales",
				"Monthly livehouse sales",
				"Monthly rental sales",
				"Weekly livehouse schedule",
				"Staff salary report");
		buttonPanel.setBackground(Color.PINK);
		
		this.setButtonActionCommands("dialog/performer_revenue",
				"dialog/monthly_livehouse_revenue",
				"dialog/monthly_rental_revenue",
				"report/livehouse_schedule",
				"dialog/staff_salary");
				
		this.setPreferredSize(new Dimension(400, 150));
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		this.setBackground(Color.PINK);
	}
	

	@Override
	public Object[][] getSQLParameterInputs() {
		// TODO Auto-generated method stub
		return null;
	}
}