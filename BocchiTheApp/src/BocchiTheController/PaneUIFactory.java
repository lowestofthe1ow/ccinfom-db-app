package BocchiTheController;

import BocchiTheGUI.elements.abs.PaneUI;
import BocchiTheGUI.elements.ui.HomeTabUI;
import BocchiTheGUI.elements.ui.MonthlyLivehouseRevenueTab;
import BocchiTheGUI.elements.ui.MonthlyLivehouseRevenueUI;
import BocchiTheGUI.elements.ui.PerformerRevenueTab;
import BocchiTheGUI.elements.ui.PerformerRevenueUI;
import BocchiTheGUI.elements.ui.ScheduleTab;
import BocchiTheGUI.elements.ui.dialog.AddAuditionUI;
import BocchiTheGUI.elements.ui.dialog.AddEquipmentUI;
import BocchiTheGUI.elements.ui.dialog.AddPerformerUI;
import BocchiTheGUI.elements.ui.dialog.AddTimeslotUI;
import BocchiTheGUI.elements.ui.dialog.HireStaffUI;
import BocchiTheGUI.elements.ui.dialog.ManageAuditionsUI;
import BocchiTheGUI.elements.ui.dialog.ManagePerformancesUI;
import BocchiTheGUI.elements.ui.dialog.RemoveStaffUI;
import BocchiTheGUI.elements.ui.dialog.UpdateStaffPositionUI;
import BocchiTheGUI.elements.ui.dialog.sub.AddEquipmentDetailsUI;
import BocchiTheGUI.elements.ui.dialog.sub.AddEquipmentTypeUI;
import BocchiTheGUI.elements.ui.dialog.sub.InputSubmissionUI;
import BocchiTheGUI.elements.ui.dialog.sub.RecordRevenueUI;
import BocchiTheGUI.elements.ui.dialog.sub.SelectStaffPositionUI;
import BocchiTheGUI.elements.ui.dialog.sub.SelectTimeslotUI;

public final class PaneUIFactory {
    /**
     * Creates a new {@link PaneUI} instance of a specific subclass based on an
     * identifier string. The following is a list of all valid strings:
     * <ul>
     * <li>{@code "dialog/hire_staff"}
     * <li>{@code "dialog/hire_staff/select_position"}
     * <li>{@code "dialog/remove_staff"}
     * <li>{@code "dialog/update_staff_position"}
     * <li>{@code "dialog/update_staff_position/select_position"}
     * <li>{@code "dialog/manage_auditions"}
     * <li>{@code "dialog/add_timeslot"}
     * </ul>
     * No instance is created if the string does not match any of the valid
     * identifiers.
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
            case "dialog/hire_staff/select_position":
                return new SelectStaffPositionUI(
                        "Confirm",
                        "button/sql/hire",
                        null, /* ALL dialog windows are closed upon success */
                        sqlData);
            case "dialog/remove_staff":
                return new RemoveStaffUI();
            case "dialog/update_staff_position":
                return new UpdateStaffPositionUI();
            case "dialog/update_staff_position/select_position":
                /*
                 * TODO: SelectStaffPositionUI is currently shared.
                 * Maybe make it separate to avoid this weird constructor?
                 */
                return new SelectStaffPositionUI(
                        "Confirm",
                        "button/sql/add_position",
                        "dialog/update_staff_position", /* Return to this dialog on close */
                        sqlData);

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

            /* Finance menu */
            case "dialog/generate_reports":
                return new HomeTabUI();

            case "dialog/performer_revenue":
                return new PerformerRevenueUI();

            /* Generate Reports */
            case "report/performer_report_day":
                return new PerformerRevenueTab(sqlData);

            case "dialog/monthly_livehouse_revenue":
                return new MonthlyLivehouseRevenueUI();

            case "report/monthly_livehouse_revenue":
                return new MonthlyLivehouseRevenueTab(sqlData);

            case "report/livehouse_schedule":
                return new ScheduleTab(sqlData);

        }
        return null;
    }
}
