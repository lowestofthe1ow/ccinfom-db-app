package BocchiTheController;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import BocchiTheGUI.GUI;
import BocchiTheGUI.components.CommandDialog;
import BocchiTheGUI.components.abs.DialogUI;
import BocchiTheGUI.components.abs.TableSelectionUI;
import BocchiTheGUI.components.ui.AddAuditionUI;
import BocchiTheGUI.components.ui.AddEquipmentUI;
import BocchiTheGUI.components.ui.AddPerformerUI;
import BocchiTheGUI.components.ui.AddTimeslotUI;
import BocchiTheGUI.components.ui.HireStaffUI;
import BocchiTheGUI.components.ui.ManageAuditionsUI;
import BocchiTheGUI.components.ui.ManagePerformancesUI;
import BocchiTheGUI.components.ui.RemoveStaffUI;
import BocchiTheGUI.components.ui.UpdateStaffPositionUI;
import BocchiTheGUI.components.ui.sub.AddEquipmentDetailsUI;
import BocchiTheGUI.components.ui.sub.AddEquipmentTypeUI;
import BocchiTheGUI.components.ui.sub.InputSubmissionUI;
import BocchiTheGUI.components.ui.sub.RecordRevenueUI;
import BocchiTheGUI.components.ui.sub.SelectStaffPositionUI;
import BocchiTheGUI.components.ui.sub.SelectTimeslotUI;

public class Controller {
    private Connection connection = null;
    private GUI gui;

    /**
     * Creates a new {@link DialogUI} instance of a specific subclass based on an
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
     *                         {@link DialogUI#getSQLParameterInputs()}. Ignored in
     *                         cases where the UI does not require it.
     * @return The newly-created instance ({@code null} if no string matched)
     */
    private DialogUI createDialogUI(String dialogIdentifier, Object[][] sqlData) {
        switch (dialogIdentifier) {
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
                return new SelectStaffPositionUI(
                        "Confirm",
                        "button/sql/add_position",
                        "dialog/update_staff_position",
                        sqlData);
            case "dialog/add_performer":
                return new AddPerformerUI();
            case "dialog/add_audition":
                return new AddAuditionUI();
            case "dialog/add_audition/select_timeslot":
                return new SelectTimeslotUI(sqlData);
            case "dialog/add_audition/select_timeslot/input_submission":
                return new InputSubmissionUI(sqlData);
            case "dialog/add_equipment":
                return new AddEquipmentUI();
            case "dialog/add_equipment/add_equipment_type":
                return new AddEquipmentTypeUI();
            case "dialog/add_equipment/add_equipment_details":
                return new AddEquipmentDetailsUI(sqlData);
            case "dialog/manage_auditions":
                return new ManageAuditionsUI();
            case "dialog/add_timeslot":
                return new AddTimeslotUI();
            case "dialog/manage_performances":
                return new ManagePerformancesUI();
            case "dialog/manage_performances/record_revenue":
                return new RecordRevenueUI(sqlData);
        }
        return null;
    }

    /**
     * Refreshes a {@link DialogUI} (i.e., updates it with data, if needed).
     * 
     * @param dialogUI         The UI to update
     * @param dialogIdentifier The identifier string associated with the UI
     */
    private void refreshDialogUI(DialogUI dialogUI, String dialogIdentifier) {
        /* TODO: Surely there's a way to simplify this? */
        switch (dialogIdentifier) {
            case "dialog/manage_auditions":
                ManageAuditionsUI auditions = (ManageAuditionsUI) dialogUI;
                auditions.loadTableData(executeProcedure("get_auditions"));
                break;
            case "dialog/update_staff_position":
                TableSelectionUI staff = (TableSelectionUI) dialogUI;
                staff.loadTableData(executeProcedure("get_staff"));
                break;
            case "dialog/remove_staff":
                TableSelectionUI activeStaff = (TableSelectionUI) dialogUI;
                activeStaff.loadTableData(executeProcedure("get_active_staff"));
                break;
            case "dialog/update_staff_position/select_position":
            case "dialog/hire_staff/select_position":
                SelectStaffPositionUI positions = (SelectStaffPositionUI) dialogUI;
                positions.loadTableData(executeProcedure("get_positions"));
                break;
            case "dialog/add_audition":
                TableSelectionUI performers = (TableSelectionUI) dialogUI;
                performers.loadTableData(executeProcedure("get_performers"));
                break;
            case "dialog/add_audition/select_timeslot":
                TableSelectionUI timeslots = (TableSelectionUI) dialogUI;
                timeslots.loadTableData(executeProcedure("get_timeslots"));
                break;
            case "dialog/add_equipment":
                TableSelectionUI equipment_types = (TableSelectionUI) dialogUI;
                equipment_types.loadTableData(executeProcedure("get_equipment_types"));
                break;
            case "dialog/manage_performances":
                TableSelectionUI performances = (TableSelectionUI) dialogUI;
                performances.loadTableData(executeProcedure("get_performances"));
                break;
        }
    }

    /**
     * Creates a {@link CommandDialog} and loads in a {@link DialogUI}.
     * 
     * @param dialogIdentifier The action command representing the UI to load into
     *                         the window
     * @param sqlData          The SQL data to pass to the new dialog, as in
     *                         {@link DialogUI#getSQLParameterInputs()}
     */
    private void showDialog(String dialogIdentifier, Object[][] sqlData) {
        DialogUI dialogUI = createDialogUI(dialogIdentifier, sqlData);

        /* Create the dialog window and wait for the UI to be loaded */
        gui.createDialog(dialogUI, dialogIdentifier, () -> {
            /* Refresh the UI */
            refreshDialogUI(dialogUI, dialogIdentifier);

            /* Update dialog window button listeners */
            dialogUI.addButtonListener((e) -> {
                String commandIdentifier = e.getActionCommand();

                /* Check if the button pressed was an SQL button */
                if (commandIdentifier.contains("button/sql/"))
                    parseButtonCommand(commandIdentifier, dialogUI.getSQLParameterInputs());

                /* Check if the button command terminates the window */
                if (dialogUI.isTerminatingCommand(commandIdentifier)) {
                    /* Only do something if the window is the last in the chain */
                    if (!commandIdentifier.contains("button/next/")) {
                        /* Attempt to get the "root dialog" */
                        String rootName = dialogUI.getRoot();

                        if (rootName == null)
                            gui.closeAllDialogs();
                        else
                            /* Close all dialogs except the "root", then refresh that */
                            gui.closeAllDialogsExcept(rootName, (rootUI) -> {
                                refreshDialogUI(rootUI, rootName);
                            });
                    } else {
                        showDialog(dialogIdentifier + commandIdentifier.substring(11),
                                dialogUI.getSQLParameterInputs());
                    }
                } else {
                    refreshDialogUI(dialogUI, dialogIdentifier);
                }
            });

            gui.showDialog(dialogIdentifier);
        });
    }

    /**
     * Parses a {@link ResultSet} object returned by an SQL query.
     * 
     * @param resultSet The result set to parse
     * @return A list of all the rows in the resulting set.
     */
    private List<Object[]> parseResultSet(ResultSet resultSet) {
        List<Object[]> rows = new ArrayList<>();

        try {
            int columnCount = resultSet.getMetaData().getColumnCount();
            while (resultSet.next()) {
                Object[] row = new Object[columnCount];

                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }

                rows.add(row);
            }
        } catch (SQLException e) {
            System.err.println("Failed to parse result set: " + e.getMessage());
            showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
        }

        return rows;
    }

    /**
     * Executes a stored procedure.
     * 
     * @param procedureName The name of the stored procedure
     * @param params        The parameters to pass to the stored procedure
     * @return A list of all the rows in the resulting set (as parsed by
     *         {@link #parseButtonCommand(String, Object[][])}). If the stored
     *         procedure does not return a result set, returns {@code null} instead.
     */
    private List<Object[]> executeProcedure(String procedureName, Object... params) {
        String callSql = generateCallSql(procedureName, params.length);
        System.out.println(callSql);

        try (CallableStatement cs = connection.prepareCall(callSql)) {
            for (int i = 0; i < params.length; i++) {
                Object param = params[i];

                if (param == null) {
                    cs.setString(i + 1, "");
                    continue;
                }

                /* Resolve parameter data types */
                if (param instanceof Integer) {
                    cs.setInt(i + 1, (Integer) param);
                } else if (param instanceof Long) {
                    cs.setLong(i + 1, (Long) param);
                } else if (param instanceof String) {
                    cs.setString(i + 1, (String) param);
                } else if (param instanceof Double) {
                    cs.setDouble(i + 1, (Double) param);
                } else if (param instanceof Timestamp) {
                    cs.setTimestamp(i + 1, (Timestamp) param);
                } else {

                    throw new IllegalArgumentException("Unsupported parameter type: " + param.getClass().getName());
                }
            }

            /* execute() returns true if a result set was returned */
            if (cs.execute() == true) {
                ResultSet resultSet = cs.getResultSet();
                System.out.println(procedureName + " executed successfully. Result set was returned");
                return parseResultSet(resultSet);
            } else {
                System.out.println(procedureName + " executed successfully. Result set was not returned");
                return null;
            }
        } catch (SQLException e) {
            /* SQL state 45000 indicates a user-defined error (business logic error) */
            if ("45000".equals(e.getSQLState())) {
                System.err.println("Business logic error in " + procedureName + ": " + e.getMessage());
                showMessageDialog(null, e.getMessage(), "Logical error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.err.println("Unexpected SQL error in " + procedureName + ": " + e.getMessage());
                showMessageDialog(null, e.getMessage(), "Unexpected SQL error", JOptionPane.ERROR_MESSAGE);
            }
            return null;
        }
    }

    /**
     * Generates an SQL string for calling a stored procedure. Parameters are
     * replaced with question marks, as in {@code CALL accept_audition(?)}
     * 
     * @param procedureName The name of the procedure
     * @param paramCount    The number of parameters
     * @return The SQL string
     */
    private String generateCallSql(String procedureName, int paramCount) {
        /* Wrap around braces */
        StringBuilder callSql = new StringBuilder("{CALL " + procedureName + "(");
        for (int i = 0; i < paramCount; i++) {
            callSql.append("?");
            if (i < paramCount - 1) {
                callSql.append(", ");
            }
        }
        callSql.append(")}");
        return callSql.toString();
    }

    private void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void parseButtonCommand(String eventString, Object[][] sqlQueries) {
        try {
            for (Object[] query : sqlQueries) {
                executeProcedure(eventString.substring(11), query);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There are no errors in Bocchi the Database Application.");
        }
    }

    private void initializeListeners() {
        /* Set menu bar listener */
        gui.setMenuBarListener((e) -> {
            showDialog(e.getActionCommand(), null);
        });

        /* Set window closing listener */
        gui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeConnection();
            }
        });
    }

    public Controller(Connection connection, GUI gui) {
        this.connection = connection;
        this.gui = gui;

        initializeListeners();
    }
}
