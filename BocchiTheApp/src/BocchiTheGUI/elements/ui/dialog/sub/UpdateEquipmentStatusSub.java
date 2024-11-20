package BocchiTheGUI.elements.ui.dialog.sub;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import BocchiTheGUI.elements.abstracts.PaneUI;
import BocchiTheGUI.elements.abstracts.TextFieldsUI;

public class UpdateEquipmentStatusSub  extends PaneUI {
	 private Object[][] sqlData;
	 private JComboBox<String> comboBox;
	 public UpdateEquipmentStatusSub (Object[][] sqlData)  {
        super("Add equipment status");
        this.sqlData = sqlData;
        
        JPanel comboPanel = new JPanel();
        comboPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5)); // Align left with spacing

        // Create a JLabel for the combo box
        JLabel label = new JLabel("Choose Status:");
        comboPanel.add(label);

        // Create a JComboBox with a border
        String[] options = {"UNDAMAGED", "MIN_DMG", "MAJ_DMG", "MISSING"};
        comboBox = new JComboBox<>(options);

        // Add a border to the combo box
        Border border = BorderFactory.createEmptyBorder(5, 5, 5, 5); // Top, Left, Bottom, Right padding
        comboBox.setBorder(border);

        // Optionally set preferred size for aesthetics
        comboBox.setPreferredSize(new Dimension(150, 25)); // Width: 150, Height: 25
        comboPanel.add(comboBox);

        // Add the comboPanel to the main panel
        add(comboPanel);
        addButtons("Confirm");
        this.setRoot("dialog/update_equipment_status");
        setButtonActionCommands("button/sql/change_equipment_status");
        addTerminatingCommands("button/sql/change_equipment_status");
	        
	        
	}

	@Override
	public Object[][] getSQLParameterInputs() {
		// TODO Auto-generated method stub
		List<Object> params = new ArrayList<>();

        Collections.addAll(params, sqlData[0]);
        params.add(comboBox.getSelectedItem());


        Object[][] retval = {
                params.toArray()
        };
        System.out.println("SQL Parameters: " + Arrays.deepToString(retval));
        return retval;
	}
}
