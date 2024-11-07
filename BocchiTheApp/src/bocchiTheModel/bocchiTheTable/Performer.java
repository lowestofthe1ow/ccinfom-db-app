package bocchiTheModel.bocchiTheTable;

public class Performer {
	
	private int id;
	
	private String performerName;
	private String contactFirstName;
	private String contactLastName;
	private int contactNo;
	

	public Performer(String performerName, String contactFirstName, String contactLastName, int contactNo) {
        this.performerName = performerName;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.contactNo = contactNo;
    }
		
	
	//Getters and maybe Setters
	
	public void setId(int id){
		this.id = id;
	}


	public int getId() {
		return id;
	}


	public String getPerformerName() {
		return performerName;
	}


	public String getContactFirstName() {
		return contactFirstName;
	}


	public String getContactLastName() {
		return contactLastName;
	}


	public int getContactNo() {
		return contactNo;
	}
	
	
	
}
