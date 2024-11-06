package bocchiTheModel.bocchiTheJunction;

public class PerformanceRevenue {
	private int performance_id;
	private double ticket_price;
	private int tickets_sold;
	private double cut_percent;
	
	PerformanceRevenue(double ticket_price, int tickets_sold, double cut_percent){
		this.ticket_price = ticket_price;
		this.tickets_sold = tickets_sold;
		this.cut_percent = cut_percent;
	}
	
	public double getCutPercent() {
		return this.cut_percent;
	}
	public void setCutPercent(double cut_percent) {
		this.cut_percent = cut_percent;
	}
	public int getPerformance_id() {
		return performance_id;
	}
	public void setPerformance_id(int performance_id) {
		this.performance_id = performance_id;
	}
	public double getTicket_price() {
		return ticket_price;
	}
	public void setTicket_price(double ticket_price) {
		this.ticket_price = ticket_price;
	}
	public int getTickets_sold() {
		return tickets_sold;
	}
	public void setTickets_sold(int tickets_sold) {
		this.tickets_sold = tickets_sold;
	}

}
