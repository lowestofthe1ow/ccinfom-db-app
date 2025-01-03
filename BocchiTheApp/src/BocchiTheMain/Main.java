package BocchiTheMain;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import BocchiTheController.Controller;
import BocchiTheGUI.GUI;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {
    public static void main(String[] args) {
        Connection connection = createConnection();

        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.setProperty("flatlaf.menuBarEmbedded", "true");

        UIManager.put("TabbedPane.showTabSeparators", true);
        UIManager.put("TabbedPane.tabsPopupPolicy", "never");
        UIManager.put("RootPane.background", Color.PINK);
        UIManager.put("TitlePane.centerTitle", Boolean.TRUE);
        UIManager.put("TitlePane.centerTitle", Boolean.TRUE);

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GUI gui = new GUI();
                // Code to run on EDT (e.g., update UI)
                new Controller(connection, gui);
            }
        });
    }

    private static Connection createConnection() {
        /* Load MySQL credentials from .env */
        Dotenv dotenv = Dotenv.load();

        String jdbcUrl = dotenv.get("JDBC_URL");
        String jdbcUser = dotenv.get("JDBC_USER");
        String jdbcPassword = dotenv.get("JDBC_PASSWORD");

        Connection connection = null;

        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } // Optional in *newer* versions of JDBC
            connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
            System.out.println("Database connection established.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
