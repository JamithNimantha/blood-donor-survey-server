package com.jamith.rmi.util;

import com.jamith.rmi.entity.Answer;
import com.jamith.rmi.entity.Question;
import com.jamith.rmi.entity.Response;
import com.jamith.rmi.entity.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * @author Jamith Nimantha
 */
public class HibernateUtil {
    private HibernateUtil() {
    }
    private static SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .loadProperties("hibernate.properties")
                .build();
        Metadata metadata =new MetadataSources(registry)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Question.class)
                .addAnnotatedClass(Answer.class)
                .addAnnotatedClass(Response.class)
                .buildMetadata();
        return metadata.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
}
