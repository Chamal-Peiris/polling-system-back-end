package lk.ijse.dep8.polling.dao;

import lk.ijse.dep8.polling.entity.SuperEntity;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

public abstract   class CrudDAOImpl<T extends SuperEntity,ID extends Serializable> implements CrudDAO<T,ID> {

    protected EntityManager em;
    private Class<T> entityClassObj;

    public CrudDAOImpl(){
        entityClassObj= (Class<T>) (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
    }

    @Override
    public T save(T entity) {
       em.merge(entity);
       return entity;
    }

    @Override
    public void deleteById(ID pk) {
        em.remove(em.getReference(entityClassObj,pk));
    }

    @Override
    public Optional<T> findById(ID pk) {
        T entity = em.find(entityClassObj, pk);
        return  entity==null?Optional.empty():Optional.of(entity);
    }

    @Override
    public List<T> findAll() {
        return em.createQuery("SELECT e FROM " + entityClassObj.getName() + " e", entityClassObj).getResultList();
    }

    @Override
    public long count() {
        return   em.createQuery("SELECT COUNT(e) FROM"+entityClassObj.getName()+"e",Long.class).getSingleResult();
    }

    @Override
    public boolean existById(ID pk) {
       return findById(pk).isPresent();
    }
}
