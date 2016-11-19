package hu.unideb.inf.rft.neuban.persistence.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


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

    @Builder
    public ColumnEntity(Long id, String title) {
        super(id);
        this.title = title;
    }
}
