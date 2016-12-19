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
@Table(name = "board")
@Entity
@NamedQueries(@NamedQuery(name = "BoardEntity.findParentBoardbyColumnId", 
query = "SELECT board FROM BoardEntity board INNER JOIN board.columns column WHERE column.id = :columnId"))
public class BoardEntity extends SuperEntity<Long> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 2, max = 30)
    @Column(name = "title")
    private String title;

    @JoinColumn(name = "board_id")
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private List<ColumnEntity> columns;

    @ManyToMany(mappedBy = "boards")
    private List<UserEntity> users;

    @PreRemove
    private void removeBoardsFromUsers() {
        for (UserEntity userEntity : users) {
            userEntity.getBoards().remove(this);
        }
    }

    @Builder
    public BoardEntity(final Long id, final String title, final List<ColumnEntity> columns) {
        super(id);
        this.title = title;
        this.columns = columns;
    }

    public static class BoardEntityBuilder {

        private List<ColumnEntity> columns = Collections.emptyList();

        public BoardEntityBuilder columns(final List<ColumnEntity> columns) {
            if (columns != null) {
                this.columns = columns;
            }
            return this;
        }
    }
}
