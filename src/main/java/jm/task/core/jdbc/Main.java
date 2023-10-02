package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserDao userDao = new UserDaoJDBCImpl();

        userDao.createUsersTable();

        userDao.saveUser("Darina", "Sergeeva", (byte) 22);
        userDao.saveUser("Mihail", "Shevchenko", (byte) 25);
        userDao.saveUser("Aleksey", "Morozov", (byte) 26);
        userDao.saveUser("Anastasia", "Sugatova", (byte) 25);

        userDao.removeUserById(2);
        userDao.getAllUsers();
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
