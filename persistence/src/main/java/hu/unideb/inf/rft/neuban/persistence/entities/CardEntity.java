package hu.unideb.inf.rft.neuban.persistence.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_card_relation_table",
            joinColumns = @JoinColumn(name = "card_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<UserEntity> users;

    @Builder
    public CardEntity(Long id, String title, String description, List<UserEntity> users) {
        super(id);
        this.title = title;
        this.description = description;
        this.users = users;
    }
}
