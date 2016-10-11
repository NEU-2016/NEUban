package hu.unideb.inf.rft.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	String userName;
	String password;

}
