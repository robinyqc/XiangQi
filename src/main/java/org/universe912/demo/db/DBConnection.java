package org.universe912.demo.db;

import java.sql.*;

public class DBConnection {
    private Connection conn;

    public DBConnection(String url) throws SQLException {
        conn = DriverManager.getConnection(url);
    }

    public void exec(String expresssionString) throws SQLException {
        Statement stmt = conn.createStatement();

        stmt.executeUpdate(expresssionString);
        stmt.close();
    }

    private void initialize() throws SQLException {
        // TODO: Initialize db if not created.
        // exec()
    }
}
