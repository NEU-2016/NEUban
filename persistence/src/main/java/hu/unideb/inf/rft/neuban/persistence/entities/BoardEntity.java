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
@Table(name = "board")
@Entity
public class BoardEntity extends SuperEntity<Long> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(name = "title")
    private String title;

    @JoinColumn(name = "board_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<ColumnEntity> columns;

    @Builder
    public BoardEntity(Long id, String title, List<ColumnEntity> columns) {
        super(id);
        this.title = title;
        this.columns = columns;
    }
}
