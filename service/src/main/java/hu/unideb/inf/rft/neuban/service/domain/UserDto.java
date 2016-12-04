package hu.unideb.inf.rft.neuban.service.domain;

import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import hu.unideb.inf.rft.neuban.service.annotations.FieldMatch;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor
@Data
@ToString(callSuper = true, exclude = {"password", "passwordConfirmation"})
@EqualsAndHashCode(callSuper = true)
@FieldMatch(firstFieldName = "password", secondFieldName = "passwordConfirmation", message = "password.fields.not.match")
public class UserDto extends BaseDto<Long> {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(min = 3, max = 20)
    private String userName;

    @NotNull
    @Size(min = 5)
    private String password;

    @NotNull
    @Size(min = 5)
    private String passwordConfirmation;

    private Role role;

    private List<BoardDto> boards;

    private List<CardDto> cards;

    @Builder
    public UserDto(final Long id, final String userName, final String password, final String passwordConfirmation,
                   final Role role, final List<BoardDto> boards, final List<CardDto> cards) {
        super(id);
        this.userName = userName;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.role = role;
        this.boards = boards;
        this.cards = cards;
    }

    public static class UserDtoBuilder {

        private Role role = Role.USER;
        private List<BoardDto> boards = Collections.emptyList();
        private List<CardDto> cards = Collections.emptyList();

        public UserDtoBuilder role(final Role role) {
            if (role != null) {
                this.role = role;
            }
            return this;
        }

        public UserDtoBuilder boards(final List<BoardDto> boards) {
            if (boards != null) {
                this.boards = boards;
            }
            return this;
        }

        public UserDtoBuilder cards(final List<CardDto> cards) {
            if (cards != null) {
                this.cards = cards;
            }
            return this;
        }
    }
}
