package de.devnytake.skrimeparty.mysql;

import java.sql.*;
import java.util.UUID;

/**
 * Copyright: DevNyTake
 * 15.07.2020 | 08:29
 */

public class MySQL {

    public static String USERNAME;
    public static String PASSWORD;
    public static String DATABASE;
    public static String HOST;
    public static String PORT;
    public static Connection CONNECTION;


    public MySQL(String username, String password, String database, String host, String port) {
        USERNAME = username;
        PASSWORD = password;
        DATABASE = database;
        HOST = host;
        PORT = port;
    }

    public static boolean connected() {
        return CONNECTION != null;
    }

    public static void connect() {
        if (!connected()) {
            try {
                CONNECTION = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE, USERNAME, PASSWORD);
                System.out.println("MySQL verbunden");
            } catch (SQLException var1) {
                var1.printStackTrace();
            }
        }

    }

    public static void close() {
        if (connected()) {
            try {
                CONNECTION.close();
            } catch (SQLException var1) {
                var1.printStackTrace();
            }
        }

    }

    public static void addTable() {
        if (connected()) {
            try {
                CONNECTION.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS stats (UUID VARCHAR(100), KILLS int, DEATHS int)");
            } catch (SQLException var1) {
                var1.printStackTrace();
            }
        }

    }

    public static void update(String query) {
        if (connected()) {
            try {
                CONNECTION.createStatement().executeUpdate(query);
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }

    }

    public static ResultSet Result(String query) {
        ResultSet r = null;

        try {
            Statement t = CONNECTION.createStatement();
            r = t.executeQuery(query);
        } catch (SQLException var3) {
            connect();
            System.err.println(var3);
        }

        return r;
    }

    public static boolean PlayerExists(UUID uuid) {
        try {
            ResultSet rs = Result("SELECT * FROM stats WHERE UUID='" + uuid + "'");
            if (rs.next()) {
                return rs.getString("UUID") != null;
            } else {
                return false;
            }
        } catch (SQLException var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public static void createPlayer(UUID uuid) {
        if (!PlayerExists(uuid)) {
            update("INSERT INTO time (UUID, KILLS, DEATHS) VALUES ('" + uuid + "', '0');");
        }
    }
}
