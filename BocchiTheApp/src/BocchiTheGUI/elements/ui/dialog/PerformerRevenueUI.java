package BocchiTheGUI.elements.ui.dialog;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;

public class PerformerRevenueUI extends TableSelectionUI {

    public PerformerRevenueUI() {
        super("Generate performer revenue summary", "ID", "Performer", "Contact person", "Contact number");

        this.setLoadDataCommand("sql/get_performers");

        this.addSearchBoxFilter("Filter by performer name", 1);
        this.addSearchBoxFilter("Filter by contact person name", 2);

        this.addButtons("Confirm");
        this.setButtonActionCommands("button/next/select_performer_month");
        this.addTerminatingCommands("button/next/select_performer_month");
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        Object[][] retval = {
                {
                        (Integer) super.getSQLParameterInputs()[0][0]
                }
        };
        return retval;
    }

    @Override
	public boolean allowEmptyDatasets() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getLoadFailureMessage() {
		// TODO Auto-generated method stub
		return "There are currently no recorded performances by any performer in the database";
	}
}
