
package hu.unideb.inf.rft.neuban.service.impl.shared;

import hu.unideb.inf.rft.neuban.persistence.entities.SuperEntity;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverter;
import hu.unideb.inf.rft.neuban.service.domain.BaseDto;
import hu.unideb.inf.rft.neuban.service.exceptions.factory.DataNotFoundExceptionFactory;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;
import hu.unideb.inf.rft.neuban.service.interfaces.shared.SingleDataUpdateService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;

import java.io.Serializable;

public class SingleDataUpdateServiceImpl<ENTITY extends SuperEntity<ID>, DTO extends BaseDto<ID>, ID extends Serializable,
        EXCEPTION extends DataNotFoundException> implements SingleDataUpdateService<DTO> {

    private final Class<EXCEPTION> exceptionType;
    private final JpaRepository<ENTITY, ID> repository;
    private final SingleDataConverter<ENTITY, DTO> singleDataConverter;

    public SingleDataUpdateServiceImpl(final Class<EXCEPTION> exceptionType, final JpaRepository repository, final SingleDataConverter singleDataConverter) {
        this.exceptionType = exceptionType;
        this.repository = repository;
        this.singleDataConverter = singleDataConverter;
    }

    @Override
    public void update(final DTO dto) throws DataNotFoundException {
        Assert.notNull(dto);

        if (dto.getId() == null) {
            throw DataNotFoundExceptionFactory.create(exceptionType, String.valueOf(dto.getId()));
        }
        this.repository.saveAndFlush(this.singleDataConverter.convertToSource(dto).get());
    }
}
