package hu.unideb.inf.rft.neuban.service.converter.impl;


import com.google.common.collect.Lists;
import hu.unideb.inf.rft.neuban.persistence.entities.SuperEntity;
import hu.unideb.inf.rft.neuban.service.converter.DataListConverter;
import hu.unideb.inf.rft.neuban.service.converter.SingleDataConverter;
import hu.unideb.inf.rft.neuban.service.domain.BaseDto;

import java.util.Collections;
import java.util.List;

public class GenericDataListConverter<ENTITY extends SuperEntity, DTO extends BaseDto> implements DataListConverter<ENTITY, DTO> {

    protected final Class<ENTITY> entityType;
    protected final Class<DTO> dtoType;
    protected final SingleDataConverter<ENTITY, DTO> singleDataConverter;

    public GenericDataListConverter(final Class<ENTITY> entityType, final Class<DTO> dtoType, final SingleDataConverter singleDataConverter) {
        this.entityType = entityType;
        this.dtoType = dtoType;
        this.singleDataConverter = singleDataConverter;
    }

    @Override
    public List<ENTITY> convertToSources(final List<DTO> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        final List<ENTITY> entities = Lists.newArrayList();
        for (DTO dto : dtos) {
            entities.add(this.singleDataConverter.convertToSource(dto).orElse(null));
        }
        return entities;
    }

    @Override
    public List<DTO> convertToTargets(final List<ENTITY> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        final List<DTO> dtos = Lists.newArrayList();
        for (ENTITY entity : entities) {
            dtos.add(this.singleDataConverter.convertToTarget(entity).orElse(null));
        }
        return dtos;
    }
}
