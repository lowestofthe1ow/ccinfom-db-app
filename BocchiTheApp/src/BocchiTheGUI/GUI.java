package BocchiTheGUI;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI extends JFrame {
    // Main components
    
    private JPanel contentPanel;

    // Buttons
    private JButton hireStaffBtn, updateStaffPosBtn, rentEquipmentBtn, schedAuditionBtn;
    private JButton resolveBookingBtn, cancelBookingBtn, recordRevenueBtn, generateReportsBtn; 
    private JButton acceptAuditionBtn;
    private JButton confirmButton;
    private JButton acceptButton, rejectButton;
    // The other pages
    
    private HireStaffUI hireStaffUI;
    private TableSelectionUI updateStaffPosUI;
    private TableSelectionUI acceptAuditionUI;
    private TableSelectionUI cancelPerformanceUI;
    /*
   
    private RentEquipmentUI rentEquipmentUI;
    private SchedAuditionUI schedAuditionUI;
    private ResolveBookingUI ResolveBookingUI;
    private CancelBooking cancelBookingUI;
    private RecordRevenue recordRevenueUI;
    private GenerateReportsUI generateReportsUI;
    */
    public void initTableColumnNames() {
    	this.updateStaffPosUI = new TableSelectionUI(new String[] {"ID", "Staff Name", "Current Position"});
    	this.acceptAuditionUI = new TableSelectionUI(new String[] {"ID", "Performer Name", "Submission Link"});
    	this.cancelPerformanceUI = new TableSelectionUI(new String[] {"ID", "Performer", "Date", "Start Time", "End Time"});    	
    }
    
    public GUI() {
        this.contentPanel = new JPanel();
        hireStaffUI = new HireStaffUI();
        initTableColumnNames();
        
        //Set up confirm button for optionpane
        confirmButton = new JButton();
    	confirmButton.setText("Confirm");
    	acceptButton = new JButton();
     	acceptButton.setText("Accept");
     	rejectButton = new JButton();
     	rejectButton.setText("Reject");
     	
     	
        
        //Set up the frame
        this.setTitle("Bocchi the GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setSize(960, 600);

        this.acceptAuditionBtn = new JButton("Accept Audition");
        this.acceptAuditionBtn.setActionCommand("accept_audition");

        this.acceptAuditionBtn.setPreferredSize(new Dimension(350, 30));
        this.acceptAuditionBtn.setFont(new Font("Dialog", Font.PLAIN, 15));
        //Set up the buttons
        this.hireStaffBtn = new JButton("Hire staff");
        this.hireStaffBtn.setActionCommand("hire");
        this.hireStaffBtn.setPreferredSize(new Dimension(350, 30));
        this.hireStaffBtn.setFont(new Font("Dialog", Font.PLAIN, 15));

        this.updateStaffPosBtn = new JButton("Update staff positions");
        this.updateStaffPosBtn.setActionCommand("add_position");
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
        mainBtnPanel.add(this.acceptAuditionBtn);
        /*
        mainBtnPanel.add(this.rentEquipmentBtn);
        mainBtnPanel.add(this.schedAuditionBtn);
        mainBtnPanel.add(this.resolveBookingBtn);
        mainBtnPanel.add(this.cancelBookingBtn);
        mainBtnPanel.add(this.recordRevenueBtn);
        mainBtnPanel.add(this.generateReportsBtn);
         */
        
     
        
        
        //Add main button panel to frame
        this.add(mainBtnPanel);
        this.setVisible(true);
        
        dialogInit();
    }
    public void setWindowListener(WindowAdapter windowAdapter) {
    	this.addWindowListener(windowAdapter);
    }
    
    public void setBtnAActionListener(ActionListener actionListener) {
        this.hireStaffBtn.addActionListener(actionListener);
    }

    public void setBtnBActionListener(ActionListener actionListener) {
        this.updateStaffPosBtn.addActionListener(actionListener);
    }

    public void setBtnCActionListener(ActionListener actionListener) {
        this.acceptAuditionBtn.addActionListener(actionListener);
    }

    public void setButtonPanelActionListener(ActionListener listener) {
    	this.confirmButton.addActionListener(listener);
    	this.acceptButton.addActionListener(listener);
    	this.rejectButton.addActionListener(listener);
    }
    public HireStaffUI getHireStaff() {
    	return this.hireStaffUI;
    }
    public void dialogInit() {
    	
    	
    	dialog.getContentPane().setLayout(new BorderLayout());
        dialog.setLocationRelativeTo(null); 
        dialog.setModal(true); 
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	
    	confirmBtnPanel.add(confirmButton);
    	confirmBtnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
    	acceptRejectBtnPanel.add(acceptButton);
    	acceptRejectBtnPanel.add(rejectButton);
    	acceptRejectBtnPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
    	
    	dialog.setSize(400, 360);
    	
    	acceptButton.setActionCommand("accept_audition");
    	rejectButton.setActionCommand("reject_audition");
    	
    	dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); 
    	
        dialog.addWindowListener(new WindowAdapter() {
             @Override
            public void windowClosing(WindowEvent e) {
            	
                dialog.setVisible(false);
                removeText();
             }
        });
    	
    	confirmButton.addActionListener(e -> {
    		
    		    dialog.setVisible(false);
    		    removeText();
        });
    	
    	acceptButton.addActionListener(e -> {
   	     	
    		
		    dialog.setVisible(false);
		    removeText();
    	});
    	rejectButton.addActionListener(e -> {
   	     	
    		
		    dialog.setVisible(false);
		    removeText();
    	});
    }
    
   
    

    public void removeText() {
    	hireStaffUI.removeText();
    }
    
    private JDialog dialog = new JDialog();
    private JPanel confirmBtnPanel = new JPanel();
    private JPanel acceptRejectBtnPanel = new JPanel();
    
    public void showDialog(String command) {
    	System.out.println("Command that is passed in setActionCommand is " + command  + ".");
    	dialog.getContentPane().removeAll();
    	 SwingUtilities.invokeLater(new Runnable() {
    	        public void run() {
    	            switch(command) {
    	                case "hire":
    	                    dialog.getContentPane().add(hireStaffUI, BorderLayout.CENTER); 
    	                    break;
    	                case "add_position":
    	                    dialog.getContentPane().add(updateStaffPosUI, BorderLayout.CENTER); 
    	                    break;
    	                case "accept_audition":
    	                	dialog.getContentPane().add(acceptAuditionUI, BorderLayout.CENTER);
    	                   
    	            }
    	            switch(command) {
    	            case "accept_audition":
    	            	dialog.getContentPane().add(acceptRejectBtnPanel, BorderLayout.SOUTH);
    	            	break;
    	            default:  dialog.getContentPane().add(confirmBtnPanel, BorderLayout.SOUTH);
    	            		  confirmButton.setActionCommand(command);
    	            }
    	            
    	            dialog.setVisible(true);
    	        }
    	    });
    }
    
    public TableSelectionUI getAccAud() {
    	return acceptAuditionUI;
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
}
