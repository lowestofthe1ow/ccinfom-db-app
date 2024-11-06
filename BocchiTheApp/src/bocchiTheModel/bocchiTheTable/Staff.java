package bocchiTheModel.bocchiTheTable;

public class Staff {
	
	private int id;
	private String firstName;
	private String lastName;
	private String contactInformation;
	
	
	public Staff(String firstName, String lastName, String contactInformation) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactInformation = contactInformation;
	}
	
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getContactInformation() {
		return contactInformation;
	}
	
}
