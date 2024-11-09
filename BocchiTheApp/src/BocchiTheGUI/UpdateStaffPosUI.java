package BocchiTheGUI;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.*;

public class UpdateStaffPosUI extends JPanel {
    //Update staff inputs
    private JTextField staffFirstNameTf, staffLastNameTf;
    private JTextField staffNewPositionTf, staffNewSalaryTf;

    //Confirm button
    //private JButton updateStaffPosConfirmBtn;

    public UpdateStaffPosUI() {
        this.setLayout((LayoutManager) new BoxLayout(this, BoxLayout.Y_AXIS));

        //Set up panels
        JPanel staffFirstNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffFirstNamePanel.add(new JLabel("First name: "));
        this.staffFirstNameTf = new JTextField();
        this.staffFirstNameTf.setColumns(15);
        staffFirstNamePanel.add(this.staffFirstNameTf);

        JPanel staffLastNamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffLastNamePanel.add(new JLabel("Last name: "));
        this.staffLastNameTf = new JTextField();
        this.staffLastNameTf.setColumns(15);
        staffLastNamePanel.add(this.staffLastNameTf);

        JPanel staffNewPositionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffNewPositionPanel.add(new JLabel("Staff position: "));
        this.staffNewPositionTf = new JTextField();
        this.staffNewPositionTf.setColumns(15);
        staffNewPositionPanel.add(this.staffNewPositionTf);

        JPanel staffNewSalaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffNewSalaryPanel.add(new JLabel("Staff salary: "));
        this.staffNewSalaryTf = new JTextField();
        this.staffNewSalaryTf.setColumns(15);
        staffNewSalaryPanel.add(this.staffNewSalaryTf);

       // this.updateStaffPosConfirmBtn = new JButton("Confirm");

        //Add panels into main update staff position panel
        this.add(staffFirstNamePanel);
        this.add(staffLastNamePanel);
        this.add(staffNewPositionPanel);
        this.add(staffNewSalaryPanel);
        //this.add(this.updateStaffPosConfirmBtn);
    }

    public String getFirstName() {
    	return staffFirstNameTf.getName();
    }
    
    public String getLastName() {
    	return staffFirstNameTf.getName();
    }

    public String getNewPosition() {
        return staffNewPositionTf.getName();
    }

    public Double getSalary() {
    	return Double.parseDouble(staffNewSalaryTf.getText());
    }
}