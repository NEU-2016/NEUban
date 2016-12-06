package hu.unideb.inf.rft.neuban.service.interfaces.shared;


import hu.unideb.inf.rft.neuban.service.domain.BaseDto;

import java.io.Serializable;
import java.util.Optional;

public interface SingleDataGetService<DTO extends BaseDto<ID>, ID extends Serializable> {

    Optional<DTO> get(ID id);
}
