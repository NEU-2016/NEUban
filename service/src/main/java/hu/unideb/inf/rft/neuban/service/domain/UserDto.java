package hu.unideb.inf.rft.neuban.service.domain;

import lombok.*;
import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import java.util.Collection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true, exclude = { "password", "passwordConfirmation" })
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseDto<Long> {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 3, max = 20)
	private String userName;

	@NotNull
	@Size(min = 5)
	private String password;

	private Collection<BoardDto> boards;

	@NotNull
	@Size(min = 5)
	private String passwordConfirmation;

	@NotNull
	private Role role = Role.USER;

	@Builder
	public UserDto(Long id, String userName, String password, String passwordConfirmation, Role role,
			Collection<BoardDto> boards) {
		super(id);
		this.userName = userName;
		this.password = password;
		this.passwordConfirmation = passwordConfirmation;
		this.role = role;
		this.boards = boards;
	}

}
