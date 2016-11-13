package hu.unideb.inf.rft.neuban.service.interfaces;

import java.io.Serializable;
import java.util.List;

import hu.unideb.inf.rft.neuban.persistence.entities.SuperEntity;

public interface BaseService<E extends SuperEntity<ID>, D, ID extends Serializable> {

	public D getById(ID id);

	public List<D> getAll();

	public void removeById(ID id);

	public ID saveOrUpdate(D dto);
}
