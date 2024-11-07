package BocchiTheGUI;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.*;

public class RentEquipmentUI extends JPanel {
    //Rent equipment inputs
    private JTextField performerNameTf;
    private JComboBox equipmentCB;
    private JTextField rentStartDateTf, rentEndDateTf;
    
    //Confirm button
    private JButton rentRequestConfirmBtn;

    public RentEquipmentUI() {
        this.setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));

        //Set up panels
        JPanel performerNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        performerNameTf.add(new JLabel("Performer name: "));
        this.performerNameTf = new JTextField();
        this.performerNameTf.setColumns(15);
        performerNamePanel.add(this.performerNameTf);

        this.equipmentCB = new JComboBox<String>();

        JPanel rentStartDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rentStartDatePanel.add(new JLabel("Start date: "));
        this.rentStartDateTf = new JTextField();
        this.rentStartDateTf.setColumns(15);
        rentStartDatePanel.add(this.rentStartDateTf);

        JPanel rentEndDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rentEndDatePanel.add(new JLabel("End date: "));
        this.rentEndDateTf = new JTextField();
        this.rentEndDateTf.setColumns(15);
        rentEndDatePanel.add(this.rentEndDateTf);

        this.rentRequestConfirmBtn = new JButton("Confirm");

        //Add panels into main rent equipment panel
        this.add(performerNamePanel);
        this.add(this.equipmentCB);
        this.add(rentStartDatePanel);
        this.add(rentEndDatePanel);
        this.add(this.rentRequestConfirmBtn);
    }

    public String getPerformerName() {
    	return performerNameTf.getName();
    }

    public String getEquipmentCBItem() {
        return this.equipmentCB.getSelectedItem().toString();
    }

    public String getRentStartDate() {
    	return rentStartDateTf.getName();
    }

    public String getRentEndDate() {
    	return rentEndDateTf.getName();
    }
}