package hu.unideb.inf.rft.neuban.service.impl;

import hu.unideb.inf.rft.neuban.persistence.entities.SuperEntity;
import hu.unideb.inf.rft.neuban.service.interfaces.BaseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

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
	@Transactional(readOnly = true)
	public List<D> getAll() {
		return modelMapper.map(repository.findAll(), listType);
	}

	@Override
	@Transactional(readOnly = true)
	public D getById(ID id) {
		Assert.notNull(id);
		E entity = this.repository.findOne(id);
		if (entity == null) {
			return null;
		}
		return modelMapper.map(entity, this.dtoType);
	}
	
//	@Override
//	@Transactional(readOnly = true)
//	public Optional<D> getById(ID id) {
//		Assert.notNull(id);
//		E entity = this.repository.findOne(id);
//		if (entity != null) {
//			return Optional.of(this.modelMapper.map(entity, this.dtoType));
//
//		}
//		return Optional.empty();
//	}

	@Transactional
	@Override
	public void removeById(ID id) {
		Assert.notNull(id);
		repository.delete(id);
	}

	@Override
	@Transactional
	public ID saveOrUpdate(D dto) {
		Assert.notNull(dto);
		E entity = modelMapper.map(dto, this.entityType);
		return repository.saveAndFlush(entity).getId();
	}
}
