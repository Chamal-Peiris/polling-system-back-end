package lk.ijse.dep8.polling.service.util;

import org.junit.jupiter.api.Test;

import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.*;

class JPAUtilTest {

    @Test
    void getEntityManageFactory() {
        //given
        EntityManagerFactory instance1 = JPAUtil.getEntityManageFactory();
        EntityManagerFactory instance2 = JPAUtil.getEntityManageFactory();
        assertEquals(instance1,instance2);


    }
}