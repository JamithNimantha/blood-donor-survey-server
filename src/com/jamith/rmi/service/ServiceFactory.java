package com.jamith.rmi.service;

import java.rmi.RemoteException;

/**
 * @author Jamith Nimantha
 */
public interface ServiceFactory extends SuperService {

    SuperService getService(ServiceType serviceType) throws RemoteException;

    enum ServiceType {
        USER, QUESTIONANSWER, RESPONSE
    }

}
