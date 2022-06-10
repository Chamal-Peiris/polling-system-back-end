package lk.ijse.dep8.polling.dao;

import lk.ijse.dep8.polling.entity.SuperEntity;

import java.io.Serializable;

public interface CrudDao<T extends SuperEntity,ID extends Serializable> extends SuperDao{
}
