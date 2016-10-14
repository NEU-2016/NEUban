package hu.unideb.inf.rft.neuban.service;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import hu.unideb.inf.rft.neuban.persistence.entities.SuperEntity;

/**
 * 
 * Every service must have these : getAll getById removeById
 * 
 * @author Headswitcher
 *
 * @param <E>
 *            entity type
 * @param <D>
 *            DTO
 * @param <ID>
 *            id
 */

public abstract class BaseServiceImpl<E extends SuperEntity<ID>, D, ID extends Serializable>
		implements BaseService<E, D, ID> {

	protected final Class<D> dtoType;
	protected final Class<E> entityType;

	private final Type listType = new TypeToken<List<D>>() {
	}.getType();

	protected ModelMapper modelMapper;
	protected JpaRepository<E, ID> repository;

	public BaseServiceImpl(Class<D> dtoType, Class<E> entityType, ModelMapper modelMapper,
			JpaRepository<E, ID> repository) {

		this.dtoType = dtoType;
		this.entityType = entityType;
		this.modelMapper = modelMapper;
		this.repository = repository;
	}

	@Override
	public List<D> getAll() {
		return modelMapper.map(repository.findAll(), listType);
	}

	@Override
	public D getById(ID id) {
		Assert.notNull(id);
		E entity = this.repository.findOne(id);
		if (entity == null) {
			return null;
		}
		return modelMapper.map(entity, this.dtoType);
	}

	@Override
	public void removeById(ID id) {
		Assert.notNull(id);
		repository.delete(id);
	}

	@Override
	public ID saveOrUpdate(D dto) {
		Assert.notNull(dto);
		E entity = modelMapper.map(dto, this.entityType);
		return repository.saveAndFlush(entity).getId();
	}
}
