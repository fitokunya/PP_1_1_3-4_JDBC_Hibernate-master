package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.getConnectionHibernate().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE if NOT EXISTS `mydatabase`.`users` (\n" +
                            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                            "  `name` VARCHAR(45) NOT NULL,\n" +
                            "  `lastName` VARCHAR(45) NOT NULL,\n" +
                            "  `age` INT(3) NOT NULL,\n" +
                            "  PRIMARY KEY (`id`),\n" +
                            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);")
                    .executeUpdate();
            transaction.commit();
            System.out.println("Table CREATE");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getConnectionHibernate().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE if EXISTS users").executeUpdate();
            transaction.commit();
            System.out.println("Table DROP");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            Session session = Util.getConnectionHibernate().openSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
            System.out.println("User SAVE");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            Session session = Util.getConnectionHibernate().openSession();
            transaction = session.beginTransaction();
            session.remove(session.get(User.class, id));
            transaction.commit();
            System.out.println("User DELETED by ID");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try {
            Session session = Util.getConnectionHibernate().openSession();
            transaction = session.beginTransaction();
            List<User> userList = session.createQuery("SELECT i FROM User i", User.class).getResultList();
            transaction.commit();
            System.out.println("Users GET ALL");
            return userList;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void cleanUsersTable() {
        try {
            Session session = Util.getConnectionHibernate().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM users")
                    .executeUpdate();
            transaction.commit();
            System.out.println("Users DELETED");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}