package bocchiTheModel.bocchiTheTable;

import java.time.LocalDate;
import java.time.LocalTime;
public class PerformanceSlot {
	
	private int id;
	private LocalDate slotDate;
	private LocalTime startTime;
	private LocalTime endTime;
	
	public PerformanceSlot(LocalDate slotDate, LocalTime startTime, LocalTime endTime) {
		this.slotDate = slotDate;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public LocalDate getSlotDate() {
		return slotDate;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	
	
}
