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

    public ServiceFactory getInstance() throws RemoteException {
        if (serviceFactory==null){
            serviceFactory=new ServiceFactoryImpl();
        }
        return serviceFactory;
    }

    @Override
    public SuperService getService(ServiceType serviceType) throws RemoteException {
        switch (serviceType){
            case USER:
                return new UserServiceImpl();
            default:
                return null;
        }
    }
}
