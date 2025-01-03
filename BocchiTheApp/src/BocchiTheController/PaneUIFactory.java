package BocchiTheController;

import BocchiTheGUI.elements.abstracts.PaneUI;
import BocchiTheGUI.elements.ui.dialog.AddAuditionUI;
import BocchiTheGUI.elements.ui.dialog.AddEquipmentUI;
import BocchiTheGUI.elements.ui.dialog.AddPerformerUI;
import BocchiTheGUI.elements.ui.dialog.AddPositionTypeUI;
import BocchiTheGUI.elements.ui.dialog.AddTimeslotUI;
import BocchiTheGUI.elements.ui.dialog.AssignStaffUI;
import BocchiTheGUI.elements.ui.dialog.CancelRentalUI;
import BocchiTheGUI.elements.ui.dialog.HireStaffUI;
import BocchiTheGUI.elements.ui.dialog.ManageAuditionsUI;
import BocchiTheGUI.elements.ui.dialog.ManagePerformancesUI;
import BocchiTheGUI.elements.ui.dialog.ManageRentalsUI;
import BocchiTheGUI.elements.ui.dialog.MonthlyLivehouseRevenueUI;
import BocchiTheGUI.elements.ui.dialog.PayRentalUI;
import BocchiTheGUI.elements.ui.dialog.PerformerRevenueUI;
import BocchiTheGUI.elements.ui.dialog.RemoveStaffUI;
import BocchiTheGUI.elements.ui.dialog.RentEquipmentUI;
import BocchiTheGUI.elements.ui.dialog.RentalSalesUI;
import BocchiTheGUI.elements.ui.dialog.StaffSalaryUI;
import BocchiTheGUI.elements.ui.dialog.UpdateEquipmentStatusUI;
import BocchiTheGUI.elements.ui.dialog.UpdateStaffPositionUI;
import BocchiTheGUI.elements.ui.dialog.sub.AddEquipmentDetailsUI;
import BocchiTheGUI.elements.ui.dialog.sub.AddEquipmentTypeUI;
import BocchiTheGUI.elements.ui.dialog.sub.AssignToPerformanceUI;
import BocchiTheGUI.elements.ui.dialog.sub.InputSubmissionUI;
import BocchiTheGUI.elements.ui.dialog.sub.RecordRevenueUI;
import BocchiTheGUI.elements.ui.dialog.sub.RemoveAssignedPerformances;
import BocchiTheGUI.elements.ui.dialog.sub.SelectPerformerForRental;
import BocchiTheGUI.elements.ui.dialog.sub.SelectPerformerMonthUI;
import BocchiTheGUI.elements.ui.dialog.sub.SelectStaffPositionUI;
import BocchiTheGUI.elements.ui.dialog.sub.SelectTimeslotUI;
import BocchiTheGUI.elements.ui.dialog.sub.SelectUpdatedStaffPositionUI;
import BocchiTheGUI.elements.ui.dialog.sub.UpdateEquipmentStatusSub;
import BocchiTheGUI.elements.ui.dialog.sub.ViewAssignedPerformancesUI;
import BocchiTheGUI.elements.ui.dialog.sub.ViewAssignedStaffUI;
import BocchiTheGUI.elements.ui.tab.MonthlyLivehouseRevenueTab;
import BocchiTheGUI.elements.ui.tab.PerformerRevenueTab;
import BocchiTheGUI.elements.ui.tab.RentalSalesTab;
import BocchiTheGUI.elements.ui.tab.ScheduleTab;
import BocchiTheGUI.elements.ui.tab.StaffSalaryTab;

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
                return new SelectStaffPositionUI(sqlData);
            case "dialog/remove_staff":
                return new RemoveStaffUI();
            case "dialog/update_staff_position":
                return new UpdateStaffPositionUI();
            case "dialog/update_staff_position/select_staff_position":
                return new SelectUpdatedStaffPositionUI(sqlData);
            case "dialog/add_position_type":
                return new AddPositionTypeUI();

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
            case "dialog/assign_staff":
                return new AssignStaffUI();
            case "dialog/assign_staff/assign_to_performance":
                return new AssignToPerformanceUI(sqlData);
            case "dialog/assign_staff/assign_to_performance/view_assigned_performances":
                return new ViewAssignedPerformancesUI(sqlData);
            case "dialog/assign_staff/view_assigned_staff":
                return new ViewAssignedStaffUI(sqlData);
            case "dialog/remove_staff_assignment/view_assigned_performances":
            	return new RemoveAssignedPerformances(sqlData);
             
            /* Equipment menu */
            case "dialog/add_equipment":
                return new AddEquipmentUI();
            case "dialog/add_equipment/add_equipment_type":
                return new AddEquipmentTypeUI();
            case "dialog/add_equipment/add_equipment_details":
                return new AddEquipmentDetailsUI(sqlData);
            case "dialog/rent_equipment":
                return new RentEquipmentUI();
            case "dialog/rent_equipment/select_performer":
                return new SelectPerformerForRental(sqlData);
            case "dialog/manage_rentals":
                return new ManageRentalsUI();
            case "dialog/pay_rental":
                return new PayRentalUI();
            case "dialog/cancel_rental":
                return new CancelRentalUI();
            case "dialog/update_equipment_status":
                return new UpdateEquipmentStatusUI();
            case "dialog/update_equipment_status/change_equipment_status":
                return new UpdateEquipmentStatusSub(sqlData);

            /* Report generation dialogs */
            case "dialog/performer_revenue":
                return new PerformerRevenueUI();
            case "dialog/performer_revenue/select_performer_month":
                return new SelectPerformerMonthUI(sqlData);
            case "dialog/monthly_livehouse_revenue":
                return new MonthlyLivehouseRevenueUI();
            case "dialog/staff_salary":
                return new StaffSalaryUI();
            case "dialog/monthly_rental_revenue":
                return new RentalSalesUI();

            /* Report tabs */
            case "report/performer_report_month":
                return new PerformerRevenueTab(sqlData);
            case "report/monthly_livehouse_revenue":
                return new MonthlyLivehouseRevenueTab(sqlData);
            case "report/livehouse_schedule":
                return new ScheduleTab(sqlData);
            case "report/staff_salary":
                return new StaffSalaryTab(sqlData);
            case "report/monthly_rental_sales":
                return new RentalSalesTab(sqlData);
        }
        return null;
    }
}
