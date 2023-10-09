package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userDao = new UserServiceImpl();

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
