package hu.unideb.inf.rft.neuban.service.impl.shared;


import hu.unideb.inf.rft.neuban.persistence.entities.SuperEntity;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverter;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataGetService;
import hu.unideb.inf.rft.neuban.service.domain.BaseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Optional;

public class SingleDataGetServiceImpl<ENTITY extends SuperEntity<ID>, DTO extends BaseDto<ID>, ID extends Serializable> implements SingleDataGetService<DTO, ID> {

    private final Class<ENTITY> entityType;
    private final Class<DTO> dtoType;
    private final JpaRepository<ENTITY, ID> repository;
    private final SingleDataConverter<ENTITY, DTO> singleDataConverter;

    public SingleDataGetServiceImpl(final Class<ENTITY> entityType, final Class<DTO> dtoType,
                                    final JpaRepository repository, final SingleDataConverter singleDataConverter) {
        this.entityType = entityType;
        this.dtoType = dtoType;
        this.repository = repository;
        this.singleDataConverter = singleDataConverter;
    }

    @Override
    public Optional<DTO> get(final ID id) {
        Assert.notNull(id);

        final Optional<ENTITY> entityOptional = Optional.ofNullable(this.repository.findOne(id));

        if (entityOptional.isPresent()) {
            return this.singleDataConverter.convertToTarget(entityOptional.get());
        }
        return Optional.empty();
    }
}
