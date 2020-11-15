package com.jamith.rmi.repository;

import com.jamith.rmi.repository.impl.AnswerRepositoryImpl;
import com.jamith.rmi.repository.impl.QuestionRepositoryImpl;
import com.jamith.rmi.repository.impl.ResponseRepositoryImpl;
import com.jamith.rmi.repository.impl.UserRepositoryImpl;

/**
 * @author Jamith Nimantha
 */
public class RepositoryFactory {

    private static RepositoryFactory repositoryFactory;

    public static RepositoryFactory getInstance() {
        if (repositoryFactory == null) {
            repositoryFactory = new RepositoryFactory();
        }
        return repositoryFactory;
    }

    public <T> T RepoFactoryFor(RepositoryTypes repositoryTypes) {
        switch (repositoryTypes) {
            case USER:
                return (T) new UserRepositoryImpl();
            case QUESTION:
                return (T) new QuestionRepositoryImpl();
            case ANSWER:
                return (T) new AnswerRepositoryImpl();
            case RESPONSE:
                return (T) new ResponseRepositoryImpl();
            default:
                return null;
        }
    }

    public enum RepositoryTypes {
        USER, QUESTION, ANSWER, RESPONSE
    }
}
