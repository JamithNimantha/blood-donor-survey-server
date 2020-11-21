package com.jamith.rmi.repository.impl;

import com.jamith.rmi.dto.ReportDTO;
import com.jamith.rmi.entity.Response;
import com.jamith.rmi.repository.ResponseRepository;
import com.jamith.rmi.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jamith Nimantha
 */
public class ResponseRepositoryImpl implements ResponseRepository, Serializable {

    private final SessionFactory sessionFactory;

    public ResponseRepositoryImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    /**
     * Save the instance of the Entity
     *
     * @param response The Entity to be saved
     * @return true if instance of the Entity Successfully saved
     * @throws Exception
     */
    @Override
    public boolean save(Response response) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(response);
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
    public boolean update(Response entity) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Response response = session.get(Response.class, entity.getId());
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
            Response response = session.get(Response.class, id);
            session.remove(response);
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
    public Response getOne(Integer id) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.get(Response.class, id);
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
    public List<Response> getAll() throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            return session.createCriteria(Response.class).list();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    @Override
    public List<Response> findAllResponsesByEmail(String email) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("SELECT R FROM response R INNER JOIN user U ON R.user.id = U.id WHERE U.email = :email", Response.class);
            query.setParameter("email", email);
            return query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Generate Report by Question id
     *
     * @param id Question ID
     * @return List of Report Data
     * @throws Exception
     */
    @Override
    public List<ReportDTO> generateReportByQuestionId(Integer id) throws Exception {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            String sql = "SELECT DISTINCT\n" +
                    "\tCOUNT(answer.`name`) AS data, \n" +
                    "\tanswer.`name` AS label\n" +
                    "FROM\n" +
                    "\tanswer\n" +
                    "\tINNER JOIN\n" +
                    "\tresponse\n" +
                    "\tON \n" +
                    "\t\tanswer.answer_id = response.answer_id\n" +
                    "\tINNER JOIN\n" +
                    "\tquestion\n" +
                    "\tON \n" +
                    "\t\tanswer.question_id = question.question_id\n" +
                    "WHERE\n" +
                    "\tquestion.question_id = ?\n" +
                    "GROUP BY\n" +
                    "\tanswer.`name`\n";
            Query query = session.createNativeQuery(sql);
            query.setParameter(1, id);
            query.unwrap(NativeQuery.class)
                    .addScalar("data", IntegerType.INSTANCE)
                    .addScalar("label", StringType.INSTANCE)
                    .setResultTransformer(Transformers.aliasToBean(ReportDTO.class));
            return query.getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return new ArrayList<>();
    }
}
