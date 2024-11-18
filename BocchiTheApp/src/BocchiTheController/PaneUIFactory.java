package BocchiTheController;

import BocchiTheGUI.elements.abstracts.PaneUI;
import BocchiTheGUI.elements.ui.dialog.AddAuditionUI;
import BocchiTheGUI.elements.ui.dialog.AddEquipmentUI;
import BocchiTheGUI.elements.ui.dialog.AddPerformerUI;
import BocchiTheGUI.elements.ui.dialog.AddTimeslotUI;
import BocchiTheGUI.elements.ui.dialog.HireStaffUI;
import BocchiTheGUI.elements.ui.dialog.ManageAuditionsUI;
import BocchiTheGUI.elements.ui.dialog.ManagePerformancesUI;
import BocchiTheGUI.elements.ui.dialog.MonthlyLivehouseRevenueUI;
import BocchiTheGUI.elements.ui.dialog.PerformerRevenueUI;
import BocchiTheGUI.elements.ui.dialog.RemoveStaffUI;
import BocchiTheGUI.elements.ui.dialog.UpdateStaffPositionUI;
import BocchiTheGUI.elements.ui.dialog.sub.AddEquipmentDetailsUI;
import BocchiTheGUI.elements.ui.dialog.sub.AddEquipmentTypeUI;
import BocchiTheGUI.elements.ui.dialog.sub.InputSubmissionUI;
import BocchiTheGUI.elements.ui.dialog.sub.RecordRevenueUI;
import BocchiTheGUI.elements.ui.dialog.sub.SelectStaffPositionUI;
import BocchiTheGUI.elements.ui.dialog.sub.SelectTimeslotUI;
import BocchiTheGUI.elements.ui.tab.MonthlyLivehouseRevenueTab;
import BocchiTheGUI.elements.ui.tab.PerformerRevenueTab;
import BocchiTheGUI.elements.ui.tab.ScheduleTab;

public final class PaneUIFactory {
    /**
     * Creates a new {@link PaneUI} instance of a specific subclass based on an
     * identifier string. No instance is created if the string does not match any of
     * the valid identifiers. Optionally, some instances may require additional
     * data. This is supplied through a 2-dimensional {@link Object} array that is
     * ignored when it is not needed.
     * 
     * @param dialogIdentifier The identifier string
     * @param sqlData          The SQL data to pass to the new dialog, as in
     *                         {@link PaneUI#getSQLParameterInputs()}. Ignored in
     *                         cases where the UI does not require it.
     * @return The newly-created instance ({@code null} if no string matched)
     */
    public static PaneUI createPaneUI(String dialogIdentifier, Object[][] sqlData) {
        switch (dialogIdentifier) {
            /* Staff menu */
            case "dialog/hire_staff":
                return new HireStaffUI();
            case "dialog/hire_staff/select_staff_position":
                return new SelectStaffPositionUI("button/sql/hire", null, sqlData);
            case "dialog/remove_staff":
                return new RemoveStaffUI();
            case "dialog/update_staff_position":
                return new UpdateStaffPositionUI();
            case "dialog/update_staff_position/select_staff_position":
                return new SelectStaffPositionUI("button/sql/add_position", "dialog/update_staff_position", sqlData);

            /* Audition/Performance menu */
            case "dialog/add_performer":
                return new AddPerformerUI();
            case "dialog/add_timeslot":
                return new AddTimeslotUI();
            case "dialog/add_audition":
                return new AddAuditionUI();
            case "dialog/add_audition/select_timeslot":
                return new SelectTimeslotUI(sqlData);
            case "dialog/add_audition/select_timeslot/input_submission":
                return new InputSubmissionUI(sqlData);
            case "dialog/manage_auditions":
                return new ManageAuditionsUI();
            case "dialog/manage_performances":
                return new ManagePerformancesUI();
            case "dialog/manage_performances/record_revenue":
                return new RecordRevenueUI(sqlData);

            /* Equipment menu */
            case "dialog/add_equipment":
                return new AddEquipmentUI();
            case "dialog/add_equipment/add_equipment_type":
                return new AddEquipmentTypeUI();
            case "dialog/add_equipment/add_equipment_details":
                return new AddEquipmentDetailsUI(sqlData);

            /* Report generation dialogs */
            case "dialog/performer_revenue":
                return new PerformerRevenueUI();
            case "dialog/monthly_livehouse_revenue":
                return new MonthlyLivehouseRevenueUI();

            /* Report tabs */
            case "report/performer_revenue":
                return new PerformerRevenueTab(sqlData);
            case "report/monthly_livehouse_revenue":
                return new MonthlyLivehouseRevenueTab(sqlData);
            case "report/livehouse_schedule":
                return new ScheduleTab(sqlData);
        }
        return null;
    }
}
