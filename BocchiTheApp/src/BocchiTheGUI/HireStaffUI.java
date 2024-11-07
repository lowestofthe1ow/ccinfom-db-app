package BocchiTheGUI;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import java.awt.*;

public class HireStaffUI {
    //Hire staff panel
    private JPanel hireStaffPanel;

    //Hire staff inputs
    private JTextField staffFirstNameTf, staffLastNameTf, staffContactInfoTf;
    private JTextField staffPositionTf, staffSalaryTf, staffStartDateTf, staffEndDateTf;

    //Confirm button
    private JButton hireStaffConfirmBtn;

    public HireStaffUI() {
        this.hireStaffPanel = new JPanel();
        this.hireStaffPanel.setLayout((LayoutManager) new BoxLayout(this.hireStaffPanel, BoxLayout.Y_AXIS));

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
        staffLastNamePanel.add(this.staffFirstNameTf);

        JPanel staffContactInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffContactInfoPanel.add(new JLabel("Contact no.: "));
        this.staffContactInfoTf = new JTextField();
        this.staffContactInfoTf.setColumns(15);
        staffContactInfoPanel.add(this.staffFirstNameTf);

        JPanel staffPositionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffPositionPanel.add(new JLabel("Staff position: "));
        this.staffPositionTf = new JTextField();
        this.staffPositionTf.setColumns(15);
        staffPositionPanel.add(this.staffFirstNameTf);

        JPanel staffSalaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffSalaryPanel.add(new JLabel("Staff salary: "));
        this.staffSalaryTf = new JTextField();
        this.staffSalaryTf.setColumns(15);
        staffSalaryPanel.add(this.staffFirstNameTf);

        JPanel staffStartDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffStartDatePanel.add(new JLabel("Start date: "));
        this.staffStartDateTf = new JTextField();
        this.staffStartDateTf.setColumns(15);
        staffStartDatePanel.add(this.staffFirstNameTf);

        JPanel staffEndDatePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        staffEndDatePanel.add(new JLabel("End date: "));
        this.staffEndDateTf = new JTextField();
        this.staffEndDateTf.setColumns(15);
        staffEndDatePanel.add(this.staffFirstNameTf);

        this.hireStaffConfirmBtn = new JButton("Confirm");


        //Add panels into main hire staff panel
        this.hireStaffPanel.add(staffFirstNamePanel);
        this.hireStaffPanel.add(staffLastNamePanel);
        this.hireStaffPanel.add(staffContactInfoPanel);
        this.hireStaffPanel.add(staffPositionPanel);
        this.hireStaffPanel.add(staffSalaryPanel);
        this.hireStaffPanel.add(staffStartDatePanel);
        this.hireStaffPanel.add(staffEndDatePanel);
        this.hireStaffPanel.add(this.hireStaffConfirmBtn);
    }
}