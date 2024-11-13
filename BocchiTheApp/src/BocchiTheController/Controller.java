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
                castedDialogUI.loadTableData(executeProcedure("get_auditions"));
                /*
                 * TODO: Sample.
                 * You can make a recursive call to showDialog() here to create more dialogs
                 */
                // showDialog("hire_staff");
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
        gui.createDialog(dialogUI, actionCommand, () -> {
            /* Refresh the UI */
            refreshDialogUI(dialogUI, actionCommand);

            /* Update dialog window button listeners */
            dialogUI.addButtonListener((e) -> {
                /* Process the command */
                parseButtonCommand(e.getActionCommand(), dialogUI.getSQLParameterInputs());

                /* If the action command is terminating, close the window */
                if (dialogUI.isTerminatingCommand(e.getActionCommand()))
                    gui.closeDialog(actionCommand);
                /* Otherwise, refresh the UI again */
                else
                    refreshDialogUI(dialogUI, actionCommand);
            });
            gui.showDialog(actionCommand);
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
                executeProcedure(eventString, query);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There are no errors in Bocchi the Database Application.");
        }
    }

    private void initializeListeners() {
        /* Set menu bar listener */
        gui.setMenuListener((e) -> {
            showDialog(e.getActionCommand());
        });

        /* Set window closing listener */
        gui.setWindowListener(new WindowAdapter() {
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
