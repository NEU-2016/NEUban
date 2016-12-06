package hu.unideb.inf.rft.neuban.persistence.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
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
	@Column(name = "createdDateTime")
	private LocalDateTime createdDateTime;

	@Column(name = "userId")
	private UserEntity user;

	@Builder
	public CommentEntity(Long id, String content, LocalDateTime createdDateTime, UserEntity user) {
		super(id);
		this.content = content;
		this.createdDateTime = createdDateTime;
		this.user = user;
	}

}
