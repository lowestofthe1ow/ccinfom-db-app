package BocchiTheGUI.elements.ui.tab;

import java.awt.Dimension;

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
		main.add(new JLabel("Welcome to Bocchi the App"));
		this.add(main);

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

		this.setPreferredSize(new Dimension(400, 150));
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
	}

	@Override
	public Object[][] getSQLParameterInputs() {
		// TODO Auto-generated method stub
		return null;
	}
}