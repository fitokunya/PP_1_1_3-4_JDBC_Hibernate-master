package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoJDBCImpl();

    //    public UserDao userDaoHibernate = new UserDaoHibernateImpl();
    @Override
    public void createUsersTable() {
        userDao.createUsersTable();
//        userDaoHibernate.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        userDao.dropUsersTable();
//        userDaoHibernate.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        userDao.saveUser(name, lastName, age);
//        userDaoHibernate.saveUser(name, lastName, age);
        System.out.println("User с именем – " + name + " добавлен в базу данных");
    }

    @Override
    public void removeUserById(long id) {
        userDao.removeUserById(id);
//        userDaoHibernate.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
//        List<User> users = userDaoHibernate.getAllUsers();
//        for (User user : users) {
//            System.out.println(user);
//        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        userDao.cleanUsersTable();
//        userDaoHibernate.cleanUsersTable();
    }
}
