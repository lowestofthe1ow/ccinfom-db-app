package BocchiTheController;

import java.awt.event.ActionListener;
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

import BocchiTheGUI.DialogUI;
import BocchiTheGUI.GUI;
import BocchiTheGUI.HireStaffUI;

public class Controller {
    private Connection connection = null;
    private GUI gui;

    private void showDialog(DialogUI dialogUI) {
        /* Show the dialog window and wait for the UI to be loaded */
        gui.showDialog(dialogUI, () -> {
            /* Update dialog window button listeners */
            gui.addDialogButtonListener((e) -> {
                gui.closeDialog();
                commandHandler(e.getActionCommand());
            });
        });
    }

    private void initializeListeners() {
        /*
         * Add a listener to the menu button
         * TODO: See if it's possible to store info of which UI to load from e
         * This will allow us to reuse the same listener for every menu button
         */
        gui.setBtnAActionListener((e) -> {
            showDialog(new HireStaffUI());
        });

        gui.setWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeConnection();
            }
        });
    }

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

    public void commandHandler(String eventString) {
        try {
            switch (eventString) {
                case "hire":
                    executeProcedure(eventString, gui.getSQLParameterInputs());
                    break;
                case "accept_audition":
                    executeProcedure(eventString,
                            gui.getAccAud().getSelectedID());
                    break;
                case "reject_audition":
                    executeProcedure(eventString,
                            gui.getAccAud().getSelectedID());
                    break;
                /*
                 * case "add_position": executeProcedure(eventString,
                 * gui.getStaffPos().getStaffID(),
                 * gui.getStaffPos().getPositionName(),
                 * gui.getStaffPos().getSalary());
                 * break;
                 * case "cancel_performance": executeProcedure(eventString,
                 * gui.getCancelPerf().getPerformanceID());
                 * break;
                 */
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
        // hireStaff("Akai Haato", "", "09156621444", "Staff", 200);
        // addPosition(1, "HEAD", 500);
        // selectStaff();
        // selectStaffPosition();
    }

    public Object[][] updateAuditionPendingList() {
        String selectSql = "SELECT a.id, p.performer_name, a.submission_link "
                + "FROM audition a "
                + "LEFT JOIN performer p ON p.id = a.performer_id "
                + "WHERE a.audition_status = 'PENDING';";

        List<Object[]> rows = new ArrayList<>();

        try (Statement statement = this.connection.createStatement();
                ResultSet resultSet = statement.executeQuery(selectSql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String performerName = resultSet.getString("performer_name");
                String submissionLink = resultSet.getString("submission_link");

                Object[] row = { id, performerName, submissionLink };
                rows.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rows.toArray(new Object[0][]);
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
