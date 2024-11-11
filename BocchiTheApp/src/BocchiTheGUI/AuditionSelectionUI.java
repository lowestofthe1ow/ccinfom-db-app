package BocchiTheGUI;

public class AuditionSelectionUI extends TableSelectionUI {
	
	AuditionSelectionUI(){
		
		 super("Audition Selection", "ID", "Performer Name", "Submission Link");
		 super.addButtons("Accept");
	     super.addButtons("Reject");
	     setButtonActionCommands("accept_audition", "reject_audition");
	}
	
	@Override
	public Object[] getSQLParameterInputs() {
		int selectedRowIndex = table.getSelectedRow();
	     
        if (selectedRowIndex == -1) {
            return null;  
        }
        
        Object[] idObject = {table.getValueAt(selectedRowIndex, 0)}; 
        if (idObject[0] instanceof Integer) {
            return idObject;  
        } else {
            throw new IllegalArgumentException("Selected row does not have an Integer ID.");
        }
		//return null;
	} 
}
