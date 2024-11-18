package BocchiTheGUI.components.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import BocchiTheGUI.components.abs.PaneUI;

public class GenerateReportUI extends PaneUI {
	public GenerateReportUI() {
		super("Generate Reports");

		this.add(new JLabel("Welcome to Bocchi the App"), BorderLayout.NORTH);

		this.addButtons("Daily performer sales",
				"Monthly livehouse sales",
				"Monthly rental sales",
				"Weekly livehouse schedule",
				"Staff salary report");

		this.setButtonActionCommands("dialog/performer_revenue",
				"tab/livehouse_sales",
				"tab/rental_sales",
				"tab/livehouse_schedule",
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