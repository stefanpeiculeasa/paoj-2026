package com.pao.project.banking.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private String url;
    private String user;
    private String password;

    private DatabaseConnection() {
        Properties props = new Properties();
        try (InputStream input = new FileInputStream("resources/db.properties")) {
            props.load(input);
            url = props.getProperty("db.url");
            user = props.getProperty("db.user", "");
            password = props.getProperty("db.password", "");
            
            initSchema();
        } catch (IOException e) {
            throw new RuntimeException("Eroare la citirea proprietatilor bazei de date", e);
        }
    }

    private void initSchema() {
        try (Connection connection = getConnection();
             java.util.Scanner s = new java.util.Scanner(new java.io.File("schema.sql")).useDelimiter(";")) {
            try (java.sql.Statement st = connection.createStatement()) {
                while (s.hasNext()) {
                    String line = s.next().trim();
                    if (!line.isEmpty()) {
                        st.execute(line);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Avertisment: Nu s-a putut rula schema.sql (" + e.getMessage() + "). Presupunem ca tabelele exista deja.");
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (user.isEmpty()) {
                return DriverManager.getConnection(url);
            } else {
                return DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la crearea conexiunii", e);
        }
    }
}
