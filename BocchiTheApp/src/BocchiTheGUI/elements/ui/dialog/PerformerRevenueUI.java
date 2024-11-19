package BocchiTheGUI.elements.ui.dialog;

import java.awt.GridLayout;
import java.sql.Timestamp;
import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import BocchiTheGUI.elements.abstracts.TableSelectionUI;
import raven.datetime.component.date.DatePicker;

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
}
