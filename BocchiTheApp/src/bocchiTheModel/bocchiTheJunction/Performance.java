package bocchiTheModel.bocchiTheJunction;

public class Performance {
	private int id;
	private int performer_id; //this could be Performer performer instead of performer_id, i think
	private int genre_id; //this could be Genre genre instead of performer_id, i think
	private int performance_slot_id; //this could be PerformanceSlot performanceSlot
	private double baseQuota;
	
	public Performance(double baseQuota) {
		this.baseQuota = baseQuota;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPerformer_id() {
		return performer_id;
	}
	public void setPerformer_id(int performer_id) {
		this.performer_id = performer_id;
	}
	public int getGenre_id() {
		return genre_id;
	}
	public void setGenre_id(int genre_id) {
		this.genre_id = genre_id;
	}

	public double getBaseQuota() {
		return baseQuota;
	}
	public void setBaseQuota(double baseQuota) {
		this.baseQuota = baseQuota;
	}
	public int getPerformance_slot_id() {
		return performance_slot_id;
	}
	public void setPerformance_slot_id(int performance_slot_id) {
		this.performance_slot_id = performance_slot_id;
	}
}
