package hu.unideb.inf.rft.neuban.persistence.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(name = "card")
@Entity
public class CardEntity extends SuperEntity<Long> {

    @NotNull
    @Size(min = 2)
    @Column(name = "title")
    private String title;

    @NotNull
    @Lob
    @Column(name = "description")
    private String description;

    @Builder
    public CardEntity(Long id, String title, String description) {
        super(id);
        this.title = title;
        this.description = description;
    }
}
