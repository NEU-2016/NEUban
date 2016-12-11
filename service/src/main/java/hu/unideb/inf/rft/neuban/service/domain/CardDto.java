package hu.unideb.inf.rft.neuban.service.domain;

import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CardDto extends BaseDto<Long> {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 2)
	private String title;

	@NotNull
	private String description;

	private List<CommentDto> comments;

	@Builder
	public CardDto(final Long id, final String title, final String description, final List<CommentDto> comments) {
		super(id);
		this.title = title;
		this.description = description;
		this.comments = comments;
	}

	public static class CardDtoBuilder {

		private String description = StringUtils.EMPTY;
		private List<CommentDto> comments = Collections.emptyList();

		public CardDtoBuilder description(final String description) {
			if (description != null) {
				this.description = description;
			}
			return this;
		}

		public CardDtoBuilder comments(final List<CommentDto> comments) {
			if (comments != null) {
				this.comments = comments;
			}
			return this;
		}

	}
}
