package com.jamith.rmi.service.impl;


import com.jamith.rmi.service.ServiceFactory;
import com.jamith.rmi.service.SuperService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Jamith Nimantha
 */
public class ServiceFactoryImpl extends UnicastRemoteObject implements ServiceFactory {
    private static final long serialVersionUID = -7032846122230981271L;
    private ServiceFactory serviceFactory;

    public ServiceFactoryImpl() throws RemoteException {
    }

    /**
     * @return instance of the Service Factory Implementation
     * @throws RemoteException
     */
    public ServiceFactory getInstance() throws RemoteException {
        if (serviceFactory==null){
            serviceFactory=new ServiceFactoryImpl();
        }
        return serviceFactory;
    }

    /**
     * Get Service Implementation by Service Name
     *
     * @param serviceType Service Type
     * @return Service Implementation
     * @throws RemoteException
     */
    @Override
    public SuperService getService(ServiceType serviceType) throws RemoteException {
        switch (serviceType){
            case USER:
                return new UserServiceImpl();
            case QUESTIONANSWER:
                return new QuestionAnswerServiceImpl();
            case RESPONSE:
            default:
                return null;
        }
    }
}
