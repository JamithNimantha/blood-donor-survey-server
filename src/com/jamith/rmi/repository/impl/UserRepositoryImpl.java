package com.jamith.rmi.repository.impl;

import com.jamith.rmi.entity.User;
import com.jamith.rmi.repository.UserRepository;
import com.jamith.rmi.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jamith Nimantha
 */
public class UserRepositoryImpl implements UserRepository, Serializable {

    private final SessionFactory sessionFactory;

    public UserRepositoryImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Save the instance of the Entity
     *
     * @param entity The Entity to be saved
     * @return true if instance of the Entity Successfully saved
     * @throws Exception
     */
    @Override
    public boolean save(User entity) throws Exception {

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * update the instance of the Entity
     *
     * @param entity The instance of the entity to be updated
     * @return true if instance of the Entity successfully updated
     * @throws Exception
     */
    @Override
    public boolean update(User entity) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * delete the instance of the entity
     *
     * @param id id of the entity to be deleted
     * @return true if the instance of the Entity successfully deleted
     * @throws Exception
     */
    @Override
    public boolean delete(Integer id) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            User user = session.get(User.class, id);
            session.remove(user);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * get the @Entity by the id
     *
     * @param id id of the entity to get
     * @return the instance of the entity
     * @throws Exception
     */
    @Override
    public User getOne(Integer id) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.get(User.class, id);
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }


    /**
     * Get all the instances of the entity
     *
     * @return All the instance of the entity as List
     * @throws Exception
     */
    @Override
    public List<User> getAll() throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.createCriteria(User.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * get an instance of the User by email
     *
     * @param email email of the user
     * @return the instance of the User
     */
    @Override
    public User findByEmail(String email) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("SELECT U FROM user U WHERE U.email = :email", User.class);
            query.setParameter("email", email);
            List<User> result = query.getResultList();
            if (!result.isEmpty())
                return result.get(0);
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }
}
