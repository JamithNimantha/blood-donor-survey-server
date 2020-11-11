package com.jamith.rmi.service;

/**
 * @author Jamith Nimantha
 */
public interface ServiceFactory extends Service {

    public enum ServiceType {
        USER
    }

    public Service getService(ServiceType serviceType) throws Exception;

}
