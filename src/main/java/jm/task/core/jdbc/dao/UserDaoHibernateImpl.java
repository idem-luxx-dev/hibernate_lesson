package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private SessionFactory factory = Util.getSessionFactory();
    private Session session;
    private Logger log = LoggerFactory.getLogger(UserDaoHibernateImpl.class);

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        session = factory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                "(id BIGINT NOT NULL AUTO_INCREMENT, name VARCHAR(255), last_name VARCHAR(255), age INT, PRIMARY KEY(id))").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        session = factory.getCurrentSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(new User(name, lastName, age));
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {

        session = factory.getCurrentSession();
        session.beginTransaction();
        session.delete((User)session.load(User.class, id));
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public List<User> getAllUsers() {
        session = factory.getCurrentSession();
        session.beginTransaction();
        List<User> instances = session.createCriteria(User.class).list();
        session.getTransaction().commit();
        session.close();

        return instances;
    }

    @Override
    public void cleanUsersTable() {
        session = factory.getCurrentSession();
        session.beginTransaction();
        final List<User> instances = session.createCriteria(User.class).list();

        for (User obj : instances) {

            session.delete(obj);

        }
        session.getTransaction().commit();
        session.close();

    }
}
