package BocchiTheGUI;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.*;

public class HireStaffUI extends JPanel {
    //Hire staff panel
 

    //Hire staff inputs
    private JTextField staffFirstNameTf, staffLastNameTf, staffContactInfoTf;
    private JTextField staffPositionTf, staffSalaryTf;
   //  private JTextField staffStartDateTf, staffEndDateTf;

    //Confirm button
   // private JButton hireStaffConfirmBtn;

    public HireStaffUI() {
        
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

        JPanel staffContactInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffContactInfoPanel.add(new JLabel("Contact no.: "));
        this.staffContactInfoTf = new JTextField();
        this.staffContactInfoTf.setColumns(15);
        staffContactInfoPanel.add(this.staffContactInfoTf);

        JPanel staffPositionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffPositionPanel.add(new JLabel("Staff position: "));
        this.staffPositionTf = new JTextField();
        this.staffPositionTf.setColumns(15);
        staffPositionPanel.add(this.staffPositionTf);

        JPanel staffSalaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffSalaryPanel.add(new JLabel("Staff salary: "));
        this.staffSalaryTf = new JTextField();
        this.staffSalaryTf.setColumns(15);
        staffSalaryPanel.add(this.staffSalaryTf);
/*
        JPanel staffStartDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffStartDatePanel.add(new JLabel("Start date: "));
        this.staffStartDateTf = new JTextField();
        this.staffStartDateTf.setColumns(15);
        staffStartDatePanel.add(this.staffStartDateTf);

        JPanel staffEndDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffEndDatePanel.add(new JLabel("End date: "));
        this.staffEndDateTf = new JTextField();
        this.staffEndDateTf.setColumns(15);
        staffEndDatePanel.add(this.staffEndDateTf);
*/
        //this.hireStaffConfirmBtn = new JButton("Confirm");


        //Add panels into main hire staff panel
        this.add(staffFirstNamePanel);
        this.add(staffLastNamePanel);
        this.add(staffContactInfoPanel);
        this.add(staffPositionPanel);
        this.add(staffSalaryPanel);
        //this.add(staffStartDatePanel);
        //this.add(staffEndDatePanel);
        //this.add(this.hireStaffConfirmBtn);
    }
    
    public String getFirstName() {
    	return staffFirstNameTf.getText();
    }
    
    public String getLastName() {
    	return staffLastNameTf.getText();
    }
    public Long getContactNo() {
    	return Long.parseLong(staffContactInfoTf.getText());
    }
  
    public String getPositionName() {
    	return staffPositionTf.getText();
    }
    public Double getSalary() {
    	return Double.parseDouble(staffSalaryTf.getText());
    }
    public void removeText() {
    	staffFirstNameTf.setText("");
    	staffLastNameTf.setText("");
    	staffPositionTf.setText("");
    	staffContactInfoTf.setText("");
    	staffSalaryTf.setText("");
    	
    }
}

