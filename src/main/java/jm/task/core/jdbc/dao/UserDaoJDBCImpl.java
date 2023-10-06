package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {
    PreparedStatement preparedStatement = null;
    public UserDaoJDBCImpl() {

    }
    public void createUsersTable() {
        try(Connection connection = getConnection()) {
            preparedStatement = connection.prepareStatement("CREATE TABLE `mydatabase`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastname` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT(3) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`))\n" +
                    "ENGINE = InnoDB\n" +
                    "DEFAULT CHARACTER SET = utf8;");
            preparedStatement.execute();
        } catch (SQLSyntaxErrorException e) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(Connection connection = getConnection()) {
            preparedStatement = connection.prepareStatement("DROP TABLE users");
            preparedStatement.execute();
        } catch (SQLSyntaxErrorException e) {
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(Connection connection = getConnection()) {
            preparedStatement = connection.prepareStatement("INSERT INTO users (name, lastname, age) VALUES (?, ?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(Connection connection = getConnection()) {
            preparedStatement = connection.prepareStatement("DELETE FROM users WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList();
        try (Connection connection = getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users")) {
            ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),resultSet.getString("lastName"),resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try(Connection connection = getConnection()) {
            preparedStatement = connection.prepareStatement("TRUNCATE TABLE users");
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
