package hu.unideb.inf.rft.neuban.persistence.entities;

import java.util.Collections;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "card")
@Entity
public class CardEntity extends SuperEntity<Long> {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(min = 2)
	@Column(name = "title")
	private String title;

	@NotNull
	@Lob
	@Column(name = "description")
	private String description;

	@JoinColumn(name = "card_id")
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
	private List<CommentEntity> comments;

	@ManyToMany(mappedBy = "cards")
	private List<UserEntity> users;

	@PreRemove
	private void removeCardsFromUsers() {
		for (UserEntity userEntity : users) {
			userEntity.getCards().remove(this);
		}
	}

	@Builder
	public CardEntity(final Long id, final String title, final String description, final List<CommentEntity> comments) {
		super(id);
		this.title = title;
		this.description = description;
		this.comments = comments;
	}

	public static class CardEntityBuilder {

		private List<CommentEntity> comments = Collections.emptyList();

		public CardEntityBuilder comments(final List<CommentEntity> comments) {
			if (comments != null) {
				this.comments = comments;
			}
			return this;
		}
	}
}
