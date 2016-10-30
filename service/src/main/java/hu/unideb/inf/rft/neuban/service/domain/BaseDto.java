package hu.unideb.inf.rft.neuban.service.domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseDto<ID extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;
	private ID id;
	
}
