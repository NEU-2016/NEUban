package hu.unideb.inf.rft.neuban.service;

import java.util.List;



/**
 * 
 * Every service must have these : getAll getById removeById
 * 
 * @author Headswitcher
 *
 * @param <T> entity type 
 */
public interface BaseService<T> {

	public List<T> getAll() throws Exception;

	public T getById(Long id) throws Exception;

	public void removeById(Long id) throws Exception;

}
