package BocchiTheGUI.elements.ui.tab;

import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import BocchiTheGUI.elements.abstracts.PaneUI;
import BocchiTheGUI.elements.abstracts.TableSelectionUI;
import BocchiTheGUI.interfaces.DataLoadable;

public class RentalSalesTab extends TableSelectionUI{
	
	 private Object[][] sqlData;
	 private JLabel revenueLabel;
	public RentalSalesTab(Object[][] sqlData){
		super("Rental Sales", "Equipment Name", "Rental Count", "Rental Cost");
		 this.sqlData = sqlData;
		 this.setLoadDataCommand("sql/equipment_rental_report");
	     this.setLoadDataParams((String[]) sqlData[0]);
	     
	     
	     JPanel labelPanel = new JPanel();
	        labelPanel.setLayout(new GridLayout(1, 2));
	        labelPanel.setBorder(new EmptyBorder(5, 20, 5, 20));

	        JLabel label = new JLabel("Rental revenue: " );
	        label.setFont(new Font("IBM Plex Sans", Font.BOLD, 18));
	        labelPanel.add(label);

	        revenueLabel = new JLabel();
	        revenueLabel.setFont(new Font("IBM Plex Sans", Font.PLAIN, 18));
	        labelPanel.add(revenueLabel);

	        this.add(labelPanel);
	        
	}


	@Override
	public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {
		super.loadData(source);
        List<Object[]> data = source.apply("sql/equipment_rental_report", sqlData[0]);

        
        double totalRentalCost = 0.0; 

        for (Object[] row : data) {
         

            if (row[2] instanceof Number) {
                totalRentalCost += ((Number) row[2]).doubleValue();
            } else if (row[2] instanceof String) {
                try {
                    totalRentalCost += Double.parseDouble((String) row[2]);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid Rental Cost value: " + row[2]);
                }
            }
        }

        this.revenueLabel.setText(String.format("PHP %.2f", totalRentalCost));
        this.revenueLabel.revalidate();
        this.revenueLabel.repaint();
       
	}

	@Override
	public Object[][] getSQLParameterInputs() {
		// TODO Auto-generated method stub
		return null;
	}
}