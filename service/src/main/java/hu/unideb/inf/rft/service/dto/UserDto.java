package hu.unideb.inf.rft.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(exclude = "password")
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseDto<Long> {

	private static final long serialVersionUID = 1L;

	private String userName;
	private String password;

}
