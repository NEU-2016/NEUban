package hu.unideb.inf.rft.neuban.service.converter.impl;


import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.SuperEntity;
import hu.unideb.inf.rft.neuban.service.converter.ListDataConverterService;
import hu.unideb.inf.rft.neuban.service.domain.BaseDto;
import org.modelmapper.ModelMapper;
import org.springframework.util.Assert;

import java.util.List;

public class ListDataConverterServiceImpl<ENTITY extends SuperEntity, DTO extends BaseDto> extends AbstractDataConverter<ENTITY, DTO> implements ListDataConverterService<ENTITY, DTO> {

    public ListDataConverterServiceImpl(final Class<ENTITY> entityType, final Class<DTO> dtoType, final ModelMapper modelMapper) {
        super(entityType, dtoType, modelMapper);
    }

    @Override
    public List<ENTITY> convertToSources(final List<DTO> dtos) {
        Assert.notNull(dtos);
        final List<ENTITY> entities = Lists.newArrayList();
        for (DTO currentDto : dtos) {
            entities.add(this.modelMapper.map(currentDto, entityType));
        }
        return entities;
    }

    @Override
    public List<DTO> convertToTargets(final List<ENTITY> entities) {
        Assert.notNull(entities);
        final List<DTO> dtos = Lists.newArrayList();
        for (ENTITY currentEntity : entities) {
            dtos.add(this.modelMapper.map(currentEntity, dtoType));
        }
        return dtos;
    }
}
