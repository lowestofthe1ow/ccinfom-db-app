package BocchiTheGUI.elements.ui.tab;

import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import BocchiTheGUI.elements.abstracts.PaneUI;
import BocchiTheGUI.elements.abstracts.TableSelectionUI;
import BocchiTheGUI.interfaces.DataLoadable;

public class RentalSalesTab extends TableSelectionUI{
	
	 private Object[][] sqlData;
	public RentalSalesTab(Object[][] sqlData){
		super("Rental Sales");
		 this.sqlData = sqlData;
	}


	@Override
	public void loadData(BiFunction<Object, Object[], List<Object[]>> source) {
		super.loadData(source);
        List<Object[]> data = source.apply("sql/equipment_rental_report", sqlData[0]);

        String[] columnNames = { "Equipment Name", "Rental Count", "Rental Cost" };

        // Create a table model and populate it with data
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (Object[] row : data) {
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);

        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);

		
	}

	@Override
	public Object[][] getSQLParameterInputs() {
		// TODO Auto-generated method stub
		return null;
	}
}