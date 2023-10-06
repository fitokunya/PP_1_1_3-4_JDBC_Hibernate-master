package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final Util util = new Util();
    Transaction transaction = null;

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {

        try (Session session = Util.getConnectionHibernate().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS `mydatabase`.`users` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NOT NULL,\n" +
                    "  `lastname` VARCHAR(45) NOT NULL,\n" +
                    "  `age` INT(3) NOT NULL,\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);" +
                    "ENGINE = InnoDB\n" +
                    "DEFAULT CHARACTER SET = utf8;")
                    .addEntity(User.class);
            transaction.commit();
        } catch (IllegalStateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getConnectionHibernate().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if(transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getConnectionHibernate().openSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getConnectionHibernate().openSession()) {
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getConnectionHibernate().openSession()) {
            transaction = session.beginTransaction();
            return session.createQuery("FROM User", User.class).list();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Collections.emptyList();
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getConnectionHibernate().openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM users")
                    .executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
