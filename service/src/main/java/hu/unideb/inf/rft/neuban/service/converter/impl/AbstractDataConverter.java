package hu.unideb.inf.rft.neuban.service.converter.impl;


import hu.unideb.inf.rft.neuban.persistence.entities.SuperEntity;
import hu.unideb.inf.rft.neuban.service.domain.BaseDto;
import org.modelmapper.ModelMapper;

public abstract class AbstractDataConverter<ENTITY extends SuperEntity, DTO extends BaseDto> {

    protected final Class<ENTITY> entityType;
    protected final Class<DTO> dtoType;

    protected final ModelMapper modelMapper;

    public AbstractDataConverter(final Class<ENTITY> entityType, final Class<DTO> dtoType, final ModelMapper modelMapper) {
        this.entityType = entityType;
        this.dtoType = dtoType;
        this.modelMapper = modelMapper;
    }
}
