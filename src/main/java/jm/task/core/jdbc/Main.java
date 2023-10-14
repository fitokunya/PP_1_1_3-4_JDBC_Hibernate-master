package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import static jm.task.core.jdbc.util.Util.closeConnectionHibernate;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Darina", "Sergeeva", (byte) 22);
        userService.saveUser("Mihail", "Shevchenko", (byte) 25);
        userService.saveUser("Aleksey", "Morozov", (byte) 26);
        userService.saveUser("Anastasia", "Sugatova", (byte) 25);

        userService.removeUserById(2);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();

        closeConnectionHibernate();
    }
}
