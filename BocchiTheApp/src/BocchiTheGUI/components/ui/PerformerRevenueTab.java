package BocchiTheGUI.components.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.PanelUI;

import BocchiTheGUI.components.abs.PaneUI;
import BocchiTheGUI.components.abs.TableSelectionUI;
import BocchiTheGUI.interfaces.DataLoadable;

public class PerformerRevenueTab extends PaneUI implements DataLoadable {
	
	
	private ArrayList<JLabel> labels;

    public PerformerRevenueTab() {
    	super("Performer Report Day");
    	this.labels = new ArrayList<>();
    	displayItPretty();
    	repaint();
    	revalidate();
    }
    
    private void displayItPretty() {
    	for(JLabel a : labels) {
    		this.add(a);
    	}
    }
    
	@Override
	public void loadData(Function<Object, List<Object[]>> source) {
		// TODO Auto-generated method stub
		List<Object[]> data = source.apply("sql/performer_report_day");
		 for (Object[] row : data) {
	           
                Object item = row[0];
                JLabel label = new JLabel(item.toString(), JLabel.LEFT);
                labels.add(label);
               // add(label);
	        }
	}

	@Override
	public Object[][] getSQLParameterInputs() {
		// TODO Auto-generated method stub
		return null;
	}

}
