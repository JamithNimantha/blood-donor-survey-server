package com.jamith.rmi.service;

import java.rmi.RemoteException;

/**
 * @author Jamith Nimantha
 */
public interface ServiceFactory extends SuperService {

    /**
     * Get Service Implementation by Service Name
     *
     * @param serviceType Service Type
     * @return Service Implementation
     * @throws RemoteException
     */
    SuperService getService(ServiceType serviceType) throws RemoteException;

    enum ServiceType {
        USER, QUESTIONANSWER, RESPONSE
    }

}
