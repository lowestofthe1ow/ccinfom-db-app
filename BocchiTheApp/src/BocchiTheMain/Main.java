package BocchiTheMain;
import java.sql.*;

import javax.swing.SwingUtilities;

import BocchiTheController.Controller;
import BocchiTheGUI.GUI;

public class Main {
	
	public static void main(String[] args) {
        
        Connection connection = createConnection();
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();  
                // Code to run on EDT (e.g., update UI)
            	new Controller(connection, gui);
            }
        });
        
    }
	
	private static Connection createConnection() {
		
		/* The SQL credentials will need to be updated based on who is running the code */
		
		 String jdbcUrl = "jdbc:mysql://localhost:3306/livehouse";
	     String jdbcUser = "root";
	     String jdbcPassword = "12345678";
	     
	     /* The SQL credentials will need to be updated based on who is running the code */
	     
	     Connection connection = null;
	   
			try {
			   
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Optional in *newer* versions of JDBC
			    connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
			    System.out.println("Database connection established.");
			} catch (SQLException e) {
			   
			    e.printStackTrace();
			}
			
			return connection;
	}


   
 
    
}

