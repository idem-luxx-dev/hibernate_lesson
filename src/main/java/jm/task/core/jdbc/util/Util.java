package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static Connection connection = null;
    private static Util inst = getInstace();
    private static SessionFactory sessionFactory;

    public Util() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = newConnection();
            }
            System.out.println("well, looks like we connected successfully!");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        return connection;
    }

    public static Util getInstace(){
        if(inst == null){
            return new Util();
        } else {
            return inst;
        }

    }

    public static Connection newConnection() throws SQLException, IOException {

        Properties props = new Properties();
        try (InputStream in = Files.newInputStream(Paths.get(Util.class.getResource("/database.properties").toURI()))) {
            props.load(in);
        } catch (Exception e) {
            System.out.println("it wont work, just give up");
        }
        String url = props.getProperty("db.url");
        System.out.println(url);
        String username = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        return DriverManager.getConnection(url, username, password);
    }

    public static SessionFactory getSessionFactory() throws HibernateException {

        try {
            Properties props = new Properties();
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("hib.properties"));

            Configuration configuration = new Configuration();
            configuration.setProperties(props);
            configuration.addAnnotatedClass(User.class);
            sessionFactory = configuration.buildSessionFactory();


        }
        catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }

        return sessionFactory;
    }
}
