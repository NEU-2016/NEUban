package hu.unideb.inf.rft.neuban.persistence.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.REMOVE })
    private List<CommentEntity> comments;

    @Builder
    public CardEntity(final Long id, final String title, final String description, final comments) {
        super(id);
        this.title = title;
        this.description = description;
	this.comments=comments;
    }

    public static class CardEntityBuilder {
    }
}
