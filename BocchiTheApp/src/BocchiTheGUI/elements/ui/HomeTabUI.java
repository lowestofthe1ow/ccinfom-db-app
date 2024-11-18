package BocchiTheGUI.elements.ui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import BocchiTheGUI.elements.abs.PaneUI;

public class HomeTabUI extends PaneUI {
	public HomeTabUI() {
		super("Generate Reports");

		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.LINE_AXIS));
		main.add(new JLabel("Welcome to Bocchi the App"));
		this.add(main);

		this.addButtons("Daily performer sales",
				"Monthly livehouse sales",
				"Monthly rental sales",
				"Weekly livehouse schedule",
				"Staff salary report");

		this.setButtonActionCommands("dialog/performer_revenue",
				"dialog/monthly_livehouse_revenue",
				"tab/rental_sales",
				"report/livehouse_schedule",
				"tab/staff_salary");

		this.setPreferredSize(new Dimension(400, 150));
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
	}

	@Override
	public Object[][] getSQLParameterInputs() {
		// TODO Auto-generated method stub
		return null;
	}
}