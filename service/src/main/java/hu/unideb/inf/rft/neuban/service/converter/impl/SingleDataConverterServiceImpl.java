package hu.unideb.inf.rft.neuban.service.converter.impl;


import hu.unideb.inf.rft.neuban.persistence.entities.SuperEntity;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverterService;
import hu.unideb.inf.rft.neuban.service.domain.BaseDto;
import org.modelmapper.ModelMapper;
import org.springframework.util.Assert;

import java.util.Optional;


public class SingleDataConverterServiceImpl<ENTITY extends SuperEntity, DTO extends BaseDto> extends AbstractDataConverter<ENTITY, DTO> implements SingleDataConverterService<ENTITY, DTO> {

    public SingleDataConverterServiceImpl(final Class<ENTITY> entityType, final Class<DTO> dtoType, final ModelMapper modelMapper) {
        super(entityType, dtoType, modelMapper);
    }

    @Override
    public Optional<ENTITY> convertToSource(final DTO dto) {
        Assert.notNull(dto);
        return Optional.ofNullable(this.modelMapper.map(dto, entityType));
    }

    @Override
    public Optional<DTO> convertToTarget(final ENTITY entity) {
        Assert.notNull(entity);
        return Optional.ofNullable(this.modelMapper.map(entity, dtoType));
    }
}
