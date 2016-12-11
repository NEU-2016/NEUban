package hu.unideb.inf.rft.neuban.service.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommentDto extends BaseDto<Long> {

	private static final long serialVersionUID = 1L;

	@NotNull
	private String content;

	@NotNull
	private LocalDateTime createdDateTime;

	@NotNull
	private UserDto user;

	@Builder
	public CommentDto(final Long id, final String content, final LocalDateTime createdDateTime, final UserDto user) {
		super(id);
		this.content = content;
		this.createdDateTime = createdDateTime;
		this.user = user;
	}

}
