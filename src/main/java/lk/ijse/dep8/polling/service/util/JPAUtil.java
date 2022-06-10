package lk.ijse.dep8.polling.service.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory emf=buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory(){
        return Persistence.createEntityManagerFactory("poll");
    }
    public static EntityManagerFactory getEntityManageFactory(){
        return emf;
    }
}
