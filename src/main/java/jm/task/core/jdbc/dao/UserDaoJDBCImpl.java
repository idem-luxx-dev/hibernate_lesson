package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Connection connection = Util.getInstace().getConnection();
    private Logger log = LoggerFactory.getLogger(UserDaoJDBCImpl.class);
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Statement st = connection.createStatement()){
            st.executeUpdate("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT)");
        } catch (SQLException e) {
            log.warn("creating table went wrong");
        }
    }

    public void dropUsersTable() {
        try(Statement st = connection.createStatement()){
            st.executeUpdate("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            log.warn("deleting went wrong");
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement pst = connection.prepareStatement("INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)")){
            pst.setString(1, name);
            pst.setString(2,lastName);
            pst.setByte(3, age);
            pst.executeUpdate();
        } catch (SQLException e) {
            log.warn("looks like you dont know what you do, saving went wrong");
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement pst = connection.prepareStatement("DELETE FROM users WHERE id = ?")) {
            pst.setLong(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            log.warn("is there a part with no error? removing went wrong");
        }
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try (ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM users")) {
            while(resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"),
                        resultSet.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            log.warn("well, you missed up again");
        }
        log.info("looks like it works ");

        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE TABLE users");
        } catch (SQLException e) {
            log.warn("you've mischosen your career path, and thats why cleaning went wrong");
        }
    }
}
