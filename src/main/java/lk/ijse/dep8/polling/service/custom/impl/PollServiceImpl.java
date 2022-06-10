package lk.ijse.dep8.polling.service.custom.impl;

import lk.ijse.dep8.polling.dao.DAOFactory;
import lk.ijse.dep8.polling.dao.SuperDAO;
import lk.ijse.dep8.polling.dao.custom.PollDAO;
import lk.ijse.dep8.polling.dto.PollDTO;
import lk.ijse.dep8.polling.entity.Poll;
import lk.ijse.dep8.polling.service.custom.PollService;
import lk.ijse.dep8.polling.service.exception.NotFoundException;
import lk.ijse.dep8.polling.service.util.EntityDTOTransformer;
import lk.ijse.dep8.polling.service.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class PollServiceImpl implements PollService {
    @Override
    public List<PollDTO> listAllPoll() {
        EntityManager em = JPAUtil.getEntityManageFactory().createEntityManager();
        PollDAO pollDAO= DAOFactory.getInstance().getDAO(em, DAOFactory.DAOType.POLL);
       try{
          return pollDAO.findAll().stream().map(EntityDTOTransformer::getPolDTO).collect(Collectors.toList());
       }finally{
           em.close();
       }

    }

    @Override
    public PollDTO getPoll(int id) throws NotFoundException{
        EntityManager em = JPAUtil.getEntityManageFactory().createEntityManager();
        PollDAO pollDAO= DAOFactory.getInstance().getDAO(em, DAOFactory.DAOType.POLL);
        try{
       return    pollDAO.findById(id).map(EntityDTOTransformer::getPolDTO).orElseThrow(()-> new NotFoundException("Invalid pol Id"));
        }finally{
            em.close();
        }
    }

    @Override
    public PollDTO savePoll(PollDTO dto) {
        EntityManager em = JPAUtil.getEntityManageFactory().createEntityManager();
        PollDAO pollDAO= DAOFactory.getInstance().getDAO(em, DAOFactory.DAOType.POLL);
        try{
            em.getTransaction().begin();
            PollDTO polDTO = EntityDTOTransformer.getPolDTO(pollDAO.save(EntityDTOTransformer.getPollEntity(dto)));
            em.getTransaction().commit();
            return polDTO;
        }catch (Throwable t){
            if(em!=null&&em.getTransaction()!=null){
                em.getTransaction().rollback();
            }
            throw  new RuntimeException("Failed to save the Poll",t);
        }
        finally{
            em.close();
        }
    }

    @Override
    public void updatePoll(PollDTO dto) throws NotFoundException {
        EntityManager em = JPAUtil.getEntityManageFactory().createEntityManager();
        PollDAO pollDAO= DAOFactory.getInstance().getDAO(em, DAOFactory.DAOType.POLL);
        try{
         if(!pollDAO.existById(dto.getId()))throw new NotFoundException("Invalid polling id");
         em.getTransaction().begin();
         pollDAO.save(EntityDTOTransformer.getPollEntity(dto));
         em.getTransaction().commit();
        }catch (Throwable t){
            if(em!=null&&em.getTransaction()!=null){
                em.getTransaction().rollback();
            }
            throw  new RuntimeException("Failed to update the Poll",t);
        }finally{
            em.close();
        }
    }

    @Override
    public void deletePoll(int id) throws RuntimeException {
        EntityManager em = JPAUtil.getEntityManageFactory().createEntityManager();
        PollDAO pollDAO= DAOFactory.getInstance().getDAO(em, DAOFactory.DAOType.POLL);
        try{
            if(!pollDAO.existById(id))throw new NotFoundException("Invalid polling id");
            em.getTransaction().begin();
            pollDAO.deleteById(id);
            em.getTransaction().commit();
        }catch (Throwable t){
            if(em!=null&&em.getTransaction()!=null){
                em.getTransaction().rollback();
            }
            throw  new RuntimeException("Failed to delete the Poll",t);
        }finally{
            em.close();
        }
    }
}
