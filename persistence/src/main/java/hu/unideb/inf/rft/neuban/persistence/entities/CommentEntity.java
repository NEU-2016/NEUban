package hu.unideb.inf.rft.neuban.persistence.entities;



import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
	private Date createdTime;

	@JoinColumn
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	private UserEntity user;

	@JoinColumn(name = "card_id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	private CardEntity card;

	@Builder
	public CommentEntity(final Long id, final String content, final Date createdDateTime,
			final UserEntity user, final CardEntity card) {
		super(id);
		this.content = content;
		this.createdTime = createdDateTime;
		this.user = user;
		this.card = card;
	}

}
