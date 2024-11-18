package BocchiTheGUI.components.ui;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import BocchiTheGUI.components.abs.PaneUI;

public class GenerateReportUI extends PaneUI {
	public GenerateReportUI() {
		super("Generate Reports");

		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.LINE_AXIS));
		// main.setPreferredSize(new Dimension(50, 50));
		main.add(new JLabel("Welcome to Bocchi the App"));
		this.add(main);

		this.addButtons("Daily performer sales",
				"Monthly livehouse sales",
				"Monthly rental sales",
				"Weekly livehouse schedule",
				"Staff salary report");

		this.setButtonActionCommands("dialog/performer_revenue",
				"tab/livehouse_sales",
				"tab/rental_sales",
				"report/livehouse_schedule",
				"tab/staff_salary");

		this.setPreferredSize(new Dimension(400, 150));
		this.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		// this.setButtonPanelLocation(BorderLayout.CENTER);
	}

	@Override
	public Object[][] getSQLParameterInputs() {
		// TODO Auto-generated method stub
		return null;
	}
}