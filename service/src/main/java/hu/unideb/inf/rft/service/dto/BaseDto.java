package hu.unideb.inf.rft.service.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
}
