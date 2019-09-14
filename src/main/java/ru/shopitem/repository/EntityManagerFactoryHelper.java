package ru.shopitem.repository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.shopitem.repository.ConfigSetting.PERSISTENCE_UNIT;

public class EntityManagerFactoryHelper {
    private static EntityManagerFactory managerFactory = null;
    private static Lock lock = new ReentrantLock();

    public static EntityManagerFactory getEntityManagerFactory() {
        lock.lock();
        if (managerFactory == null) {
            managerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT);
        }

        lock.unlock();
        return managerFactory;
    }
}
