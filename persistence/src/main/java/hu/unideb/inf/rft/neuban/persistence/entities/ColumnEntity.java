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
@Table(name = "board_column")
@Entity
public class ColumnEntity extends SuperEntity<Long> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 2, max = 20)
    @Column(name = "title")
    private String title;

    @JoinColumn(name = "column_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<CardEntity> cards;

    @Builder
    public ColumnEntity(Long id, String title, List<CardEntity> cards) {
        super(id);
        this.title = title;
        this.cards = cards;
    }
}
