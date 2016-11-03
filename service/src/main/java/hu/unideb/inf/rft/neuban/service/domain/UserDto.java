package hu.unideb.inf.rft.neuban.service.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString(callSuper = true, exclude = "password")
@EqualsAndHashCode(callSuper = true)
public class UserDto extends BaseDto<Long> {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 3, max = 20)
	private String userName;

	@NotNull
	@Size(min = 5, max = 20)
	private String password;
	
	private Collection<BoardDto> boards;

	@Builder
	public UserDto(Long id, String userName, String password , Collection<BoardDto> boards) {
		super(id);
		this.userName = userName;
		this.password = password;
		this.boards = boards;
	}

}
