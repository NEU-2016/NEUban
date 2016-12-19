package hu.unideb.inf.rft.neuban.persistence.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;


@NoArgsConstructor
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
    public ColumnEntity(final Long id, final String title, final List<CardEntity> cards) {
        super(id);
        this.title = title;
        this.cards = cards;
    }

    public static class ColumnEntityBuilder {

        private List<CardEntity> cards = Collections.emptyList();

        public ColumnEntityBuilder cards(final List<CardEntity> cards) {
            if (cards != null) {
                this.cards = cards;
            }
            return this;
        }
    }
}
