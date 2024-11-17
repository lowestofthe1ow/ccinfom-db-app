package BocchiTheGUI.components.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import BocchiTheGUI.components.abs.DialogUI;

public class GenerateReportUI extends DialogUI {

	public GenerateReportUI() {
		super("Generate Reports");
		this.addButtons("Performer Sales Day", 
						"Live House Sales Month",
						"Rental Sales Month",
						"Livehouse schedule Week",
						"Staff Salary Report");
		
		this.setButtonActionCommands("button/tab/performer_sales", 
									 "button/tab/livehouse_sales", 
									 "button/tab/rental_sales", 
									 "button/tab/livehouse_schedule",
									 "button/tab/staff_salary");
		this.setPreferredSize(new Dimension(400, 150));
		this.setButtonPanelLocation(BorderLayout.CENTER);
	}

	@Override
	public Object[][] getSQLParameterInputs() {
		// TODO Auto-generated method stub
		return null;
	}
	
}