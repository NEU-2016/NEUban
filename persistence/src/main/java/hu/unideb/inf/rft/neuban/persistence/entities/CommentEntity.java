package hu.unideb.inf.rft.neuban.persistence.entities;



import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "comment")
@Entity
public class CommentEntity extends SuperEntity<Long> {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Lob
	@Column(name = "content")
	private String content;

	@Column(name = "created_time")
	private LocalDateTime createdTime;

	@JoinColumn
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	private UserEntity user;

	@JoinColumn(name = "card_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE })
	private CardEntity card;

	@Builder
	public CommentEntity(final Long id, final String content, final LocalDateTime createdDateTime,
			final UserEntity user, final CardEntity card) {
		super(id);
		this.content = content;
		this.createdTime = createdDateTime;
		this.user = user;
		this.card = card;
	}

}
