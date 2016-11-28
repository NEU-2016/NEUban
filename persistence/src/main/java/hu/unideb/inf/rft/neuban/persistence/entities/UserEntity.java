package hu.unideb.inf.rft.neuban.persistence.entities;

import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = "password")
@Table(name = "user")
@Entity
public class UserEntity extends SuperEntity<Long> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 3, max = 20)
    @Column(name = "user_name", unique = true)
    private String userName;

    @NotNull
    @Size(min = 5)
    @Column(name = "password")
    private String password;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_board_relation_table",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "board_id", referencedColumnName = "id"))
    private List<BoardEntity> boards;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Builder
    public UserEntity(Long id, String userName, String password, Role role, List<BoardEntity> boards) {
        super(id);
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.boards = boards;
    }
}
