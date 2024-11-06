package bocchiTheModel.bocchiTheTable;

public class Equipment {
	
	private int id;
    private String equipmentName;
    private String equipmentType;
    private double rentalFee;

    public Equipment(String equipmentName, String equipmentType, double rentalFee) {
        this.equipmentName = equipmentName;
        this.equipmentType = equipmentType;
        this.rentalFee = rentalFee;
    }
    
    // How do i implement auto increment lmao
    public void setId(int id) {
		this.id = id;
	}
    
	public int getId() {
		return id;
	}

	public String getEquipmentName() {
		return equipmentName;
	}


	public String getEquipmentType() {
		return equipmentType;
	}


	public double getRentalFee() {
		return rentalFee;
	}

}
