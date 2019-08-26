package com.Fyle.BankSearch.Connection;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnect {

    public static Connection getConnection() {
        try {
            URI dbUri = new URI(System.getenv("DATABASE_URL"));

            String username = dbUri.getUserInfo().split(":")[0];
            String password = dbUri.getUserInfo().split(":")[1];
            String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()
                    + "?sslmode=require";

            Connection connection = DriverManager.getConnection(dbUrl, username, password);
            return connection;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}