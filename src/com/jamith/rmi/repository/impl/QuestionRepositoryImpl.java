package com.jamith.rmi.repository.impl;

import com.jamith.rmi.entity.Question;
import com.jamith.rmi.repository.QuestionRepository;
import com.jamith.rmi.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jamith Nimantha
 */
public class QuestionRepositoryImpl implements QuestionRepository, Serializable {

    private final SessionFactory sessionFactory;

    public QuestionRepositoryImpl() {
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
    public boolean save(Question entity) throws Exception {
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
    public boolean update(Question entity) throws Exception {
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
            Question question = session.get(Question.class, id);
            session.remove(question);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return false;
    }

    /**
     * get the @Entity by the id
     *
     * @param id id of the entity to get
     * @return the instance of the entity
     * @throws Exception
     */
    @Override
    public Question getOne(Integer id) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.get(Question.class, id);
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
    public List<Question> getAll() throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.createCriteria(Question.class).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }
}
