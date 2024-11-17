package BocchiTheController;

import BocchiTheGUI.components.abs.DialogUI;
import BocchiTheGUI.components.ui.PerformerRevenueTab;

public final class TabUIFactory {
	
	
	public static DialogUI  createTabUI(String tabIdentifier, Object[][] sqlData) {
		switch(tabIdentifier) {
			case "tab/performer_revenue":
				return new PerformerRevenueTab();
		}
		return null;
	}
	
	
}
