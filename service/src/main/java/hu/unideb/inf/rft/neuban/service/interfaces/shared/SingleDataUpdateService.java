package hu.unideb.inf.rft.neuban.service.interfaces.shared;

import hu.unideb.inf.rft.neuban.service.domain.BaseDto;
import hu.unideb.inf.rft.neuban.service.exceptions.data.DataNotFoundException;

public interface SingleDataUpdateService<DTO extends BaseDto> {

    void update(DTO dto) throws DataNotFoundException;
}
