package BocchiTheController;

import java.awt.event.ActionListener;
import java.sql.*;
import BocchiTheGUI.GUI;

public class Controller {
	private Connection connection = null;
	private GUI gui;
	
	private void initializeListeners() {
        
        gui.setBtnAActionListener(e -> {
            gui.showDialog("Hire Staff");
        });

    }
	
	/*
	 * String firstName = gui.getHireStaff().getFirstName();
            String lastName = gui.getHireStaff().getLastName();
            String contactNo = gui.getHireStaff().getContactNo();
            String positionName = gui.getHireStaff().getPositionName();
            double salary = gui.getHireStaff().getSalary();
            
         
            hireStaff(firstName, lastName, contactNo, positionName, salary);
	 */
	
    private void hireStaff(String firstName, String lastName, String contactNo, String positionName, double salary) {
        String callSql = "{CALL hire(?, ?, ?, ?, ?)}"; 
        
        
        try (CallableStatement cs = connection.prepareCall(callSql)) {
           
            cs.setString(1, firstName);
            cs.setString(2, lastName);
            cs.setString(3, contactNo);
            cs.setString(4, positionName);
            cs.setDouble(5, salary);

            cs.execute();
            System.out.println("Staff member hired successfully.");
        } catch (SQLException e) { //might do a rollback if there's exception hmmm... TODO
            e.printStackTrace();
        }
    }

    
    private void selectStaff() {
    	String selectSql = "SELECT * FROM staff";
    	try (Statement statement = this.connection.createStatement(); ResultSet resultSet = statement.executeQuery(selectSql)) {
    	    while (resultSet.next()) {
    	        int id = resultSet.getInt("id");
    	        String firstName = resultSet.getString("first_name");
    	        String lastName = resultSet.getString("last_name");
    	        String contactNo = resultSet.getString("contact_no");

    	        System.out.println("ID: " + id +  
    	        				   ", First Name: " + firstName + 
    	        				   ", Last Name: " + lastName + 
    	        				   ", Contact No: " + contactNo);
    	    }
    	}catch(SQLException e) {
    		e.printStackTrace();
    	}
    }
    
    private void selectStaffPosition() {
        String selectSql = "SELECT * FROM staff_position";
        try (Statement statement = this.connection.createStatement(); ResultSet resultSet = statement.executeQuery(selectSql)) {
            while (resultSet.next()) {
                int staffId = resultSet.getInt("staff_id");
                String positionName = resultSet.getString("staff_position_name");
                double salary = resultSet.getDouble("staff_salary");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");

                System.out.println("Staff ID: " + staffId +
                                   ", Position: " + positionName +
                                   ", Salary: " + salary +
                                   ", Start Date: " + startDate +
                                   ", End Date: " + (endDate != null ? endDate : "Currently Employed"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
	
	public Controller(Connection connection, GUI gui) {
		
		this.connection = connection;
		this.gui = gui;
		
		initializeListeners();
        selectStaff();
        selectStaffPosition();
	}

	
	
	
}
