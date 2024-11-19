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
import BocchiTheGUI.elements.abstracts.PaneUI;
import BocchiTheGUI.elements.components.CommandDialog;
import BocchiTheGUI.interfaces.DataLoadable;

public class Controller {
    private Connection connection = null;
    private GUI gui;

    /**
     * Loads a {@link DataLoadable} with data obtained using the command that it
     * specifies. Does nothing if the UI is not a DataLoadable instance. If the UI
     * does not specify an SQL command, it
     * loads an empty dataset.
     * 
     * @param dialogUI The UI to load data into
     * @return {@code true} if the load operation succeeded {@code false} otherwise
     */
    private boolean loadDataFromSQL(PaneUI dialogUI) {
        if (dialogUI instanceof DataLoadable) {
            DataLoadable loadableUI = (DataLoadable) dialogUI;

            try {
                loadableUI.loadData((sqlIdentifier, sqlParams) -> {
                    /* If there is no SQL command for loading data, return an empty dataset */
                    if (sqlIdentifier == null) {
                        return new ArrayList<Object[]>();
                    }

                    String sqlCommand = ((String) sqlIdentifier).substring(4);
                    List<Object[]> data;

                    /* Check whether to use the passed parameters */
                    if (sqlParams == null) {
                        data = this.executeProcedure(sqlCommand);
                    } else {
                        data = this.executeProcedure(sqlCommand, sqlParams);
                    }

                    if (data.size() == 0) {
                        throw new IllegalArgumentException("No data in the database exists for this query");
                    }

                    return data;
                });
            } catch (Exception e) {
                showMessageDialog(null, e.getMessage(), "Database error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a {@link CommandDialog} and loads in a {@link PaneUI}.
     * 
     * @param dialogIdentifier The action command representing the UI to load into
     *                         the window
     * @param sqlData          The SQL data to pass to the new dialog, as in
     *                         {@link PaneUI#getSQLParameterInputs()}
     */
    private void showDialog(String dialogIdentifier, Object[][] sqlData) {
        PaneUI dialogUI = PaneUIFactory.createPaneUI(dialogIdentifier, sqlData);

        /* Create the dialog window and wait for the UI to be loaded */
        gui.createDialog(dialogUI, dialogIdentifier, () -> {
            /* Load data into the UI if it requires it */
            if (!this.loadDataFromSQL(dialogUI))
                return;

            /* Update dialog window button listeners */
            dialogUI.addButtonListener((e) -> {
                String commandIdentifier = e.getActionCommand();

                /* Check if the button pressed was an SQL button */
                if (commandIdentifier.contains("button/sql/"))
                    parseButtonCommand(commandIdentifier, dialogUI.getSQLParameterInputs());
                /* Otherwise, check if it was a report generation button */
                else if (commandIdentifier.contains("button/report/")) {
                    /* Create the tab UI */
                    PaneUI ui = PaneUIFactory.createPaneUI(
                            commandIdentifier.substring(7), /* Tab pane name */
                            dialogUI.getSQLParameterInputs() /* SQL data */);
                    /* Load data into the new tab */
                    if (this.loadDataFromSQL(ui))
                        gui.addTab(ui, commandIdentifier.substring(7));
                }

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
                                this.loadDataFromSQL(rootUI);
                            });
                    } else {
                        showDialog(dialogIdentifier + commandIdentifier.substring(11),
                                dialogUI.getSQLParameterInputs());
                    }
                } else {
                    if (!this.loadDataFromSQL(dialogUI))
                        return;
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

        gui.setGenerateReportsListener((e) -> {
            String commandIdentifier = e.getActionCommand();
            /* TODO: Helper function for this */
            if (commandIdentifier.contains("report/")) {
                /* Create the tab UI */
                System.out.println(commandIdentifier);
                PaneUI ui = PaneUIFactory.createPaneUI(
                        commandIdentifier, /* Tab pane name */
                        null);
                /* Load data into the new tab */
                if (this.loadDataFromSQL(ui))
                    gui.addTab(ui, commandIdentifier);
            } else
                showDialog(e.getActionCommand(), null);
        });
    }

    public Controller(Connection connection, GUI gui) {
        this.connection = connection;
        this.gui = gui;

        initializeListeners();
    }
}
