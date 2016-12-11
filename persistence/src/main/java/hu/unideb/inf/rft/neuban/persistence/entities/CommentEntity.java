package hu.unideb.inf.rft.neuban.persistence.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
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

	@NotNull
	@Column(name = "created_time")
	private LocalDateTime createdTime;

	@NotNull
	@Column(name = "user_id")
	private UserEntity user;

	@Builder
	public CommentEntity(final Long id,final String content,final LocalDateTime createdDateTime,final UserEntity user) {
		super(id);
		this.content = content;
		this.createdTime = createdDateTime;
		this.user = user;
	}

}
