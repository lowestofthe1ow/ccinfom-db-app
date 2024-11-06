package Main;
import java.sql.*;

public class Main {
	// loading sql driver to program
	private static void createConnection() {
		String jdbcUrl = "jdbc:mysql://localhost:3306/livehouse";
        String jdbcUser = "root";
        String jdbcPassword = "12345678";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String insertSql = "INSERT INTO performer (id, performer_name, contact_first_name, contact_last_name, contact_no) VALUES ('1', 'Haachama', 'Akai', 'Haato', '810')";
        try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
 
            int rowsAffected = statement.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
        }catch(SQLException e) {
        	e.printStackTrace();
        }
        
        
        String selectSql = "SELECT * FROM performer";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(selectSql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String performerName = resultSet.getString("performer_name");
                String firstName = resultSet.getString("contact_first_name");
                String lastName = resultSet.getString("contact_last_name");
                String contactNo = resultSet.getString("contact_no");

                System.out.println("ID: " + id + ", Name: " + performerName + ", First Name: " + firstName + ", Last Name: " + lastName + ", Contact No: " + contactNo);
            }
        }catch(SQLException e) {
        	e.printStackTrace();
        }
				
		
	}
	
    public static void main(String[] args) {
        createConnection();
    }
}
