package hu.unideb.inf.rft.neuban.service.domain;

import hu.unideb.inf.rft.neuban.persistence.enums.Role;
import hu.unideb.inf.rft.neuban.service.annotations.FieldMatch;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
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

    @NotNull
    private Role role = Role.USER;

    private List<BoardDto> boards;

    @Builder
    public UserDto(Long id, String userName, String password, String passwordConfirmation, Role role,
                   List<BoardDto> boards) {
        super(id);
        this.userName = userName;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
        this.role = role;
        this.boards = boards;
    }

}
