package bocchiTheModel.bocchiTheJunction;


import bocchiTheModel.bocchiTheEnums.AuditionStatus;

public class Audition {
	 	private int id;
	    private int performerId; //this could be Performer performer instead of performer_id, i think
	    private int slotId;
	    private String submissionLink;
	    private AuditionStatus status;
	    
	    Audition(String submissionLink, AuditionStatus status){
	    	this.submissionLink = submissionLink;
	    	this.status = status;
	    }
	    
	    
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getPerformerId() {
			return performerId;
		}
		public void setPerformerId(int performerId) {
			this.performerId = performerId;
		}
		public int getSlotId() {
			return slotId;
		}
		public void setSlotId(int slotId) {
			this.slotId = slotId;
		}
		public String getSubmissionLink() {
			return submissionLink;
		}
		public AuditionStatus getStatus() {
			return status;
		}
	    
		
		
}
