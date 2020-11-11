package com.jamith.rmi.repository.impl;

import com.jamith.rmi.entity.User;
import com.jamith.rmi.repository.UserRepo;
import com.jamith.rmi.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * @author Jamith Nimantha
 */
public class UserRepoImpl implements UserRepo {

    private SessionFactory sessionFactory;

    public UserRepoImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public boolean save(User entity) throws Exception {

        try(Session session = sessionFactory.openSession()) {
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

    @Override
    public boolean update(User entity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        return false;
    }

    @Override
    public List<User> getAll() throws Exception {
        return null;
    }
}
