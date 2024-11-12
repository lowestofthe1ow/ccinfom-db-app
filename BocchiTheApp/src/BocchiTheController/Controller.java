package BocchiTheController;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import BocchiTheGUI.GUI;
import BocchiTheGUI.components.CommandDialog;
import BocchiTheGUI.components.abs.DialogUI;
import BocchiTheGUI.components.ui.AuditionSelectionUI;
import BocchiTheGUI.components.ui.HireStaffUI;
import BocchiTheGUI.components.ui.TimeSlotMakerUI;

public class Controller {
    private Connection connection = null;
    private GUI gui;

    /**
     * Creates a new {@link DialogUI} instance of a specific subclass based on an
     * action command string.
     * <ul>
     * <li>{@code hire_staff}: {@link HireStaffUI}
     * <li>{@code audition}: {@link AuditionSelectionUI}
     * <li>{@code add_timeslot}: {@link TimeSlotMakerUI}
     * </ul>
     * No instance is created if the string does not match any of the options.
     * 
     * @param actionCommand The action command string
     * @return The newly-created instance ({@code null} if no string matched)
     */
    private DialogUI createDialogUI(String actionCommand) {
        switch (actionCommand) {
            case "hire_staff":
                return new HireStaffUI();
            case "audition":
                return new AuditionSelectionUI();
            case "add_timeslot":
                return new TimeSlotMakerUI();
        }
        return null;
    }

    /**
     * Refreshes a {@link DialogUI} (i.e., updates it with data, if needed).
     * 
     * @param dialogUI      The UI to update
     * @param actionCommand The action command string associated with the UI
     */
    private void refreshDialogUI(DialogUI dialogUI, String actionCommand) {
        switch (actionCommand) {
            case "audition":
                AuditionSelectionUI castedDialogUI = (AuditionSelectionUI) dialogUI;
                castedDialogUI.loadTableData(updateAuditionPendingList());
        }
    }

    /**
     * Creates a {@link CommandDialog} and loads in a {@link DialogUI}.
     * 
     * @param actionCommand The action command representing the UI to load into the
     *                      window
     */
    private void showDialog(String actionCommand) {
        DialogUI dialogUI = createDialogUI(actionCommand);

        /* Create the dialog window and wait for the UI to be loaded */
        gui.createDialog(dialogUI, () -> {
            /* Refresh the UI */
            refreshDialogUI(dialogUI, actionCommand);
            /* Update dialog window button listeners */
            dialogUI.addButtonListener((e) -> {
                /* Process the command */
                commandHandler(e.getActionCommand(), dialogUI.getSQLParameterInputs());
                /* If the action command is terminating, close the window */
                if (dialogUI.isTerminatingCommand(e.getActionCommand()))
                    gui.closeDialog();
                /* Otherwise, refresh the UI again */
                else
                    refreshDialogUI(dialogUI, actionCommand);
            });
            gui.showDialog();
        });
    }

    private void initializeListeners() {
        gui.setMenuListener((e) -> {
            showDialog(e.getActionCommand());
        });

        gui.setWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeConnection();
            }
        });
    }

    /* TODO: Clean up */
    public void executeProcedure(String procedureName, Object... params) throws SQLException {
        String callSql = generateCallSql(procedureName, params.length);

        try (CallableStatement cs = connection.prepareCall(callSql)) {
            for (int i = 0; i < params.length; i++) {
                Object param = params[i];
                if (param == null) {
                    cs.setString(i + 1, "");
                    continue;
                }
                if (param instanceof Integer) {
                    cs.setInt(i + 1, (Integer) param);
                } else if (param instanceof Long) {
                    cs.setLong(i + 1, (Long) param);
                } else if (param instanceof String) {
                    cs.setString(i + 1, (String) param);
                } else if (param instanceof Double) {
                    cs.setDouble(i + 1, (Double) param);
                } else {
                    throw new IllegalArgumentException("Unsupported parameter type: " + param.getClass().getName());
                }
            }
            cs.execute();

            System.out.println(procedureName + " executed successfully.");

        } catch (SQLException e) {
            if ("45000".equals(e.getSQLState())) {
                System.err.println("Business Logic Error in " + procedureName + ": " + e.getMessage());
                showMessageDialog(null, e.getMessage());
            } else {
                throw e;
            }
        }
    }

    private String generateCallSql(String procedureName, int paramCount) {
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

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void commandHandler(String eventString, Object[][] sqlQueries) {
        try {
            for (Object[] query : sqlQueries) {
                executeProcedure(eventString, query);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There are no errors in Bocchi the Database Application.");
        }
    }

    public Controller(Connection connection, GUI gui) {
        this.connection = connection;
        this.gui = gui;

        initializeListeners();
    }

    public List<Object[]> updateAuditionPendingList() {
        String selectSql = "SELECT a.audition_id, p.performer_name, a.submission_link "
                + "FROM audition a "
                + "LEFT JOIN performer p ON p.performer_id = a.performer_id "
                + "WHERE a.audition_status = 'PENDING';";

        return updateList(selectSql);
    }

    public List<Object[]> updateList(String selectSql) {
        List<Object[]> rows = new ArrayList<>();

        try (Statement statement = this.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectSql)) {
            int columnCount = resultSet.getMetaData().getColumnCount();

            while (resultSet.next()) {

                Object[] row = new Object[columnCount];

                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }

                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows;
    }

    private void selectStaff() {
        String selectSql = "SELECT * FROM staff";
        try (Statement statement = this.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectSql)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String contactNo = resultSet.getString("contact_no");

                System.out.println("ID: " + id +
                        ", First Name: " + firstName +
                        ", Last Name: " + lastName +
                        ", Contact No: " + contactNo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void selectStaffPosition() {
        String selectSql = "SELECT * FROM staff_position";
        try (Statement statement = this.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectSql)) {
            while (resultSet.next()) {
                int staffId = resultSet.getInt("staff_id");
                String positionName = resultSet.getString("staff_position_name");
                double salary = resultSet.getDouble("staff_salary");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");

                System.out.println("Staff ID: " + staffId +
                        ", Position: " + positionName +
                        ", Salary: " + salary +
                        ", Start Date: " + startDate +
                        ", End Date: " + (endDate != null ? endDate : "Currently Employed"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
