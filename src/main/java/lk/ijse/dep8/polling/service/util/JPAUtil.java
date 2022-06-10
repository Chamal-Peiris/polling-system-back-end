package lk.ijse.dep8.polling.service.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.Properties;

public abstract class JPAUtil {
    private static EntityManagerFactory emf=buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory(){
        Properties properties = new Properties();
        try {
            String profile=System.getProperty("app.profiles.active","test");
            properties.load(  JPAUtil.class.getResourceAsStream(profile.equals("test")?"/application-test.properties":"/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Persistence.createEntityManagerFactory("poll",properties);
    }
    public static EntityManagerFactory getEntityManageFactory(){
        return emf;
    }
}
