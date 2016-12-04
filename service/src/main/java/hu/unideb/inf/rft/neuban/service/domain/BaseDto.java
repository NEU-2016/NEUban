package hu.unideb.inf.rft.neuban.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class BaseDto<ID extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;
    private ID id;
}
