package jm.task.core.jdbc.util;
// UPDATE HIBER 1

import jm.task.core.jdbc.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


public class Util {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "Qwer123580...";

    private static SessionFactory sessionFactory;
    private static Connection connection = null;

    private Util() {
    }

    public static SessionFactory getConnectionHibernate() {
        if (sessionFactory == null) {
            try {
                Properties settings = new Properties();
                settings.put(Environment.URL, DB_URL + "?useSSL=false");
                settings.put(Environment.USER, DB_USERNAME);
                settings.put(Environment.PASS, DB_PASSWORD);

                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create");

                sessionFactory = new Configuration()
                        .addProperties(settings)
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory();

                System.out.println("Connection SUCCESS");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Connection ERROR");
            }
        }
        return sessionFactory;
    }

    public static void closeConnectionHibernate() {
        getConnectionHibernate().close();
        System.out.println("Connection CLOSE");
    }

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connection SUCCESS");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection ERROR");
        }
        return connection;
    }

    public static Connection closeConnection() {
        try {
            if (connection != null) { // checked the status of object
                getConnection().close();
                System.out.println("Connection CLOSE");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
