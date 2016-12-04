package hu.unideb.inf.rft.neuban.persistence.entities;

import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;


@NoArgsConstructor
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

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_card_relation_table",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "card_id", referencedColumnName = "id"))
    private List<CardEntity> cards;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Builder
    public UserEntity(final Long id, final String userName, final String password, final Role role,
                      final List<BoardEntity> boards, final List<CardEntity> cards) {
        super(id);
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.boards = boards;
        this.cards = cards;
    }

    public static class UserEntityBuilder {

        private Role role = Role.USER;
        private List<BoardEntity> boards = Collections.emptyList();
        private List<CardEntity> cards = Collections.emptyList();

        public UserEntityBuilder role(final Role role) {
            if (role != null) {
                this.role = role;
            }
            return this;
        }

        public UserEntityBuilder boards(final List<BoardEntity> boards) {
            if (boards != null) {
                this.boards = boards;
            }
            return this;
        }

        public UserEntityBuilder cards(final List<CardEntity> cards) {
            if (cards != null) {
                this.cards = cards;
            }
            return this;
        }
    }
}
