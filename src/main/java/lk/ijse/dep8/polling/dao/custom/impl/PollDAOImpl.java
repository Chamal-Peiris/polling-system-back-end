package lk.ijse.dep8.polling.dao.custom.impl;

import lk.ijse.dep8.polling.dao.CrudDAOImpl;
import lk.ijse.dep8.polling.dao.custom.PollDAO;
import lk.ijse.dep8.polling.entity.Poll;

import javax.persistence.EntityManager;

public class PollDAOImpl extends CrudDAOImpl<Poll,Integer> implements PollDAO {

    public PollDAOImpl(EntityManager em){
        this.em=em;
    }
}
