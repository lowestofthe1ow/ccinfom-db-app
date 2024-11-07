package BocchiTheGUI;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    // Main components
    
    private JPanel contentPanel;

    // Buttons
    private JButton hireStaffBtn, updateStaffPosBtn, rentEquipmentBtn, schedAuditionBtn;
    private JButton resolveBookingBtn, cancelBookingBtn, recordRevenueBtn, generateReportsBtn; 

    // The other pages
    
    private HireStaffUI hireStaffUI;
    
    /*
    private UpdateStaffPosUI updateStaffPosUI;
    private RentEquipmentUI rentEquipmentUI;
    private SchedAuditionUI schedAuditionUI;
    private ResolveBookingUI ResolveBookingUI;
    private CancelBooking cancelBookingUI;
    private RecordRevenue recordRevenueUI;
    private GenerateReportsUI generateReportsUI;
    */
   
    public GUI() {
        this.contentPanel = new JPanel();
        this.hireStaffUI = new HireStaffUI();
        
        //Set up the frame
        this.setTitle("Bocchi the GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setSize(960, 600);


        //Set up the buttons
        this.hireStaffBtn = new JButton("Hire staff");
        this.hireStaffBtn.setPreferredSize(new Dimension(350, 30));
        this.hireStaffBtn.setFont(new Font("Dialog", Font.PLAIN, 15));

        this.updateStaffPosBtn = new JButton("Update staff positions");
        this.updateStaffPosBtn.setPreferredSize(new Dimension(350, 30));
        this.updateStaffPosBtn.setFont(new Font("Dialog", Font.PLAIN, 15));

        this.rentEquipmentBtn = new JButton("Rent out equipment for audition or performance");
        this.rentEquipmentBtn.setPreferredSize(new Dimension(350, 30));
        this.rentEquipmentBtn.setFont(new Font("Dialog", Font.PLAIN, 15));

        this.schedAuditionBtn = new JButton("Schedule an audition");
        this.schedAuditionBtn.setPreferredSize(new Dimension(350, 30));
        this.schedAuditionBtn.setFont(new Font("Dialog", Font.PLAIN, 15));

        this.resolveBookingBtn = new JButton("Resolve the status of a booking");
        this.resolveBookingBtn.setPreferredSize(new Dimension(350, 30));
        this.resolveBookingBtn.setFont(new Font("Dialog", Font.PLAIN, 15));

        this.cancelBookingBtn = new JButton("Cancel an existing booking");
        this.cancelBookingBtn.setPreferredSize(new Dimension(350, 30));
        this.cancelBookingBtn.setFont(new Font("Dialog", Font.PLAIN, 15));

        this.recordRevenueBtn = new JButton("Record a performance's revenue");
        this.recordRevenueBtn.setPreferredSize(new Dimension(350, 30));
        this.recordRevenueBtn.setFont(new Font("Dialog", Font.PLAIN, 15));
        
        this.generateReportsBtn = new JButton("Generate reports");
        this.generateReportsBtn.setPreferredSize(new Dimension(350, 30));
        this.generateReportsBtn.setFont(new Font("Dialog", Font.PLAIN, 15));


        //Set up the main button panel
        JPanel mainBtnPanel = new JPanel(new GridLayout(8, 1, 10, 5));
        mainBtnPanel.setBorder(new EmptyBorder(120, 30, 120, 30));

        //Add buttons to main button panel
        mainBtnPanel.add(this.hireStaffBtn);
        mainBtnPanel.add(this.updateStaffPosBtn);
        mainBtnPanel.add(this.rentEquipmentBtn);
        mainBtnPanel.add(this.schedAuditionBtn);
        mainBtnPanel.add(this.resolveBookingBtn);
        mainBtnPanel.add(this.cancelBookingBtn);
        mainBtnPanel.add(this.recordRevenueBtn);
        mainBtnPanel.add(this.generateReportsBtn);

        
        //Add main button panel to frame
        this.add(mainBtnPanel);
        this.setVisible(true);
        
        
    }

    public void setBtnAActionListener(ActionListener actionListener) {
        this.hireStaffBtn.addActionListener(actionListener);
    }

    public void setBtnBActionListener(ActionListener actionListener) {
        this.updateStaffPosBtn.addActionListener(actionListener);
    }

    public void setBtnCActionListener(ActionListener actionListener) {
        this.rentEquipmentBtn.addActionListener(actionListener);
    }

    public void setBtnDActionListener(ActionListener actionListener) {
        this.schedAuditionBtn.addActionListener(actionListener);
    }

    public void setBtnEActionListener(ActionListener actionListener) {
        this.resolveBookingBtn.addActionListener(actionListener);
    }

    public void setBtnFActionListener(ActionListener actionListener) {
        this.cancelBookingBtn.addActionListener(actionListener);
    }

    public void setBtnGActionListener(ActionListener actionListener) {
        this.recordRevenueBtn.addActionListener(actionListener);
    }

    public void setBtnHActionListener(ActionListener actionListener) {
        this.generateReportsBtn.addActionListener(actionListener);
    }
    
    public HireStaffUI getHireStaff() {
    	return this.hireStaffUI;
    }
    
    public void showDialog(String command) {
    	switch(command) {
    	case "Hire Staff": JDialog hireStaffDialog = new JDialog(this, "Hire New Staff", true); 
					        hireStaffDialog.setContentPane(hireStaffUI); 
					        hireStaffDialog.setSize(400, 300); 
					        hireStaffDialog.setLocationRelativeTo(this);
					        hireStaffDialog.setVisible(true); 
					        break;
    	}
    }
}
