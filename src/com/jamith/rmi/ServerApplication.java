package com.jamith.rmi;

import com.jamith.rmi.service.impl.ServiceFactoryImpl;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Jamith Nimantha
 */
public class ServerApplication {
    public static void main(String[] args) {
        System.out.println("Connecting to Server.......");
        try {
            stopServer(); // Stops Server
            Registry registry = LocateRegistry.createRegistry(6666);
            registry.rebind("survey", new ServiceFactoryImpl().getInstance());
            System.out.println("====== Server is up and running ======");
        } catch (RemoteException e) {
            System.out.println("An Error Occurred. Server Failure : "+ e.getMessage());
        }
    }

    private static void stopServer() {
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(6666);
            UnicastRemoteObject.unexportObject(registry, true);
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }
}
