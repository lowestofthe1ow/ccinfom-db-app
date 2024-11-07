package bocchiTheModel.bocchiTheJunction;

import java.time.LocalDate;

import bocchiTheModel.bocchiTheEnums.EquipmentStatus;
import bocchiTheModel.bocchiTheEnums.RentalPaymentStatus;

public class EquipmentRental {
	private int performerId;
	private int equipmentId;
	private LocalDate startDate;
	private LocalDate endDate;
	private EquipmentStatus equipmentStatus;
	private RentalPaymentStatus rentalPaymentStatus;
	public int getPerformerId() {
		return performerId;
	}
	public void setPerformerId(int performerId) {
		this.performerId = performerId;
	}
	public int getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public EquipmentStatus getEquipmentStatus() {
		return equipmentStatus;
	}
	public void setEquipmentStatus(EquipmentStatus equipmentStatus) {
		this.equipmentStatus = equipmentStatus;
	}
	public RentalPaymentStatus getRentalPaymentStatus() {
		return rentalPaymentStatus;
	}
	public void setRentalPaymentStatus(RentalPaymentStatus rentalPaymentStatus) {
		this.rentalPaymentStatus = rentalPaymentStatus;
	}

}
