package BocchiTheGUI.elements.ui.dialog;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;
import BocchiTheGUI.elements.components.LabelForm;

public class ManageAuditionsUI extends TableSelectionUI {
    private JTextField baseQuotaField;

    public ManageAuditionsUI() {
        super("Manage Auditions", "ID", "Performer Name", "Submission Link", "Target timeslot",
                "Contact person", "Contact no.", "Status");

        this.setLoadDataCommand("sql/get_auditions");

        this.addSearchBoxFilter("Filter by performer name", 1);
        this.addSearchBoxFilter("Filter by contact person name", 4);
        this.addDatePickerFilter("Filter by target timeslot", 3);
        this.addComboBoxFilter("Filter by status", 6,
                "PENDING", "PASSED", "REJECTED");

        /* Base quota input */
        this.baseQuotaField = new JTextField();
        this.baseQuotaField.setPreferredSize(new Dimension(300, 30));
        this.add(new LabelForm("Base quota", baseQuotaField));

        this.addButtons("Accept", "Reject");
        this.setButtonActionCommands("button/sql/accept_audition", "button/sql/reject_audition");
    }

    @Override
    public Object[][] getSQLParameterInputs() {
        List<Object[]> retval = new ArrayList<>();

        for (Object[] row : super.getSQLParameterInputs()) {
            Object[] add = { row[0], this.baseQuotaField.getText() };
            retval.add(add);
        }

        return retval.toArray(new Object[retval.size()][]);
    }
}