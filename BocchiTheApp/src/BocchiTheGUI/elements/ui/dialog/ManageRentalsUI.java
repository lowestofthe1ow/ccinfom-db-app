package BocchiTheGUI.elements.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;
import BocchiTheGUI.elements.components.LabelForm;

public class ManageRentalsUI extends TableSelectionUI {
    private JComboBox<String> comboBox;

    public ManageRentalsUI() {
        super("Manage Rentals", "ID", "Equipment Name", "Performer Name",
            "Start Date", "End Date", "Equipment Status");
        this.setLoadDataCommand("sql/get_equipment_rentals");
        
        this.addSearchBoxFilter("Filter by equipment name", 1);
        this.addSearchBoxFilter("Filter by performer name", 2);

        this.addComboBox("Resolve equipment status as:", "UNDAMAGED", "MIN_DMG", "MAJ_DMG", "MISSING", "PENDING");

        this.addButtons("Resolve equipment status");
		this.setButtonActionCommands("button/sql/resolve_equipment_status");
    }

    public void addComboBox(String label, String... options) {
        this.comboBox = new JComboBox<String>();
        
        for (String option : options) {
            this.comboBox.addItem(option);
        }

        this.add(new LabelForm(label, comboBox));
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        List<Object> params = new ArrayList<>();

        params.add(super.getSQLParameterInputs()[0][0]);
        params.add(this.comboBox.getSelectedItem().toString());
        System.out.println(params.get(0));
        System.out.println(params.get(1));

        Object[][] retval = {
            params.toArray()
        };

        return retval;
    }
    
    @Override
    public String getLoadFailureMessage() {
        return "No Rental data found";
    }
}
